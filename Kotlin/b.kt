
import ch.qos.logback.classic.LoggerContext
import guru.nidi.graphviz.attribute.Label
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import guru.nidi.graphviz.model.Factory.*
import guru.nidi.graphviz.model.Node
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.jvm.Throws

val mp = HashMap<String, Node>()

fun main(args: Array<String>) {
    (LoggerFactory.getILoggerFactory() as LoggerContext).stop()

    val arg =
        if (args.isEmpty())
            Scanner(System.`in`)
                .apply{ println("Enter package name:") }
                .next()
                .lowercase(Locale.getDefault())
        else
            args[0]

    if (arg == "exit")
        return

    mp[arg] = node(arg).with(Label.raw(arg))
    prc(arg, c = ::lnk, d = arg)
    grf()
}

fun grf() {
    var grf = graph("Graph").directed()
    for (i in mp)
        grf = grf.with(i.value)

    /*Graphviz.fromGraph(grf)
        .render(Format.PNG)
        .toFile(File("/home/admin/Downloads/graph.png"))*/

    println()
    Graphviz.fromGraph(grf)
        .render(Format.DOT)
        .toOutputStream(System.out)
}

fun lnk(a: String, b: String) {
    val dn: Node
    val nd = mp[a]
    val pr = mp[b]!!

    dn = nd ?: node(a).with(Label.raw(a))
    mp[b] = pr.link(to(dn))
    mp[a] = dn
}

fun prc(
    a: String,
    b: Int = 4,
    d: String,
    c: (a: String, b: String) -> Unit
) {
    val jsn =
        try { pypi(a) }
        catch (e: Exception) { null }

    if (jsn == null) {
        err("Unable to connect to the PyPi site for |$a|")
        return
    }

    val ln =
        try { gtln(jsn) } catch (e: JSONException) {
            err("Unable to parse the response from the PyPi site for |$a|")
            Pair(null, null)
        } ?: return

    val lg = if (ln.first != null) frmt(ln.first!!) ?:
        run { err("Unable to format the source code url for |$a| $ln"); return }
             else {
                 err("Source code isn't available on GitHub for $a," +
                         " unable to retrieve the setup.py file")
                 null
             }

    val dpn = if (lg != null) dpnd(lg, ln.second) ?:
        run { err("Unable to extract dependencies from the setup.py file for |$a|"); return }
            else ln.second

    print("__debug__")
    for (j in 0 until b)
        print(' ')
    println(a)

    c(a, d)

    for (i in dpn ?: return)
        prc(i, b + 4, a, c)
}

@Throws(IOException::class, InterruptedException::class)
fun pypi(a: String, b: String? = null): String? {
    val cl = HttpClient.newHttpClient()
    val rq = HttpRequest.newBuilder()
        .uri(if (b == null)
                URI(
                    "https",
                    "pypi.org",
                    "/pypi/$a/json",
                    null)
                    .normalize()
            else
                URI.create(b))
        .build()

    val rsp = cl.send(rq, HttpResponse.BodyHandlers.ofString())

    return (
        if (rsp.statusCode() == 200)
            rsp.body()
        else if (rsp.statusCode() == 301)
            pypi(a, rsp.headers().firstValue("location").get())
        else
            null)
}

@Throws(JSONException::class)
fun gtln(a: String): Pair<String?, List<String>?>? {
    val inf = "info"
    val rqs = "requires_dist"
    val rls = "project_urls"

    val obj = JSONObject(a)

    if (!obj.has(inf) or obj.isNull(inf))
        return null
    val info = obj.getJSONObject(inf)

    val rqms =
        if (info.has(rqs) and !info.isNull(rqs))
            info.getJSONArray(rqs)
        else null

    val lmb = { b: JSONArray ->
        val ar = ArrayList<String>()

        for (i in b.withIndex()) {
            var j = b.getString(i.index)

            if (j == null || j.contains("extra == "))
                continue

            if (j.contains(' '))
                j = j.split (' ')[0]

            ar.add(j)
        }
        ar
    }

    if (!info.has(rls) or info.isNull(rls))
        return if (rqms != null)
            Pair(null, lmb(rqms))
        else
            null

    for (i in info.getJSONObject(rls).toMap()) {
        val l = i.value as String

        if (l.contains("github.com") &&
            (l.count { it == '/' } == 4 ||
                    l.count { it == '/' } == 5 &&
                    l.lastIndexOf('/') == l.length - 1))
            return Pair(l, if (rqms != null) lmb(rqms) else null)
    }
    return if (rqms != null) Pair(null, lmb(rqms)) else null
}

fun frmt(a: String) =
    if (a.contains("github.com"))
        a.replace(
            "github.com",
            "raw.githubusercontent.com") +
                "/master/setup.py"
    else null

fun dpnd(a: String, b: List<String>?): List<String>? {
    val dc =
        try { Jsoup.connect(a).get() }
        catch (e: IOException) { return null }
        catch (e: HttpStatusException) { return null }

    var src = dc.text()
    val bgn = src.indexOf("install_requires=[")
    val end = src.indexOf(']', bgn)

    val ar = ArrayList<String>()
    val lmb = {
        if (b != null) {
            for (i in b)
                if (!ar.contains(i)) ar.add(i)
        }
    }

    if (bgn < 0 || end < 0) {
        lmb()
        return ar
    }
    src = src.substring(bgn, end)

    var bg: Int
    var en: Int

    for (i in src.split(',')) {
        bg = i.indexOf('"') + 1
        en = i.indexOfAny(charArrayOf('=', '<', '>'), bg)

        if (bg < 0 || en < 0) continue
        ar.add(i.substring(bg, en))
    }

    lmb()
    return ar
}

fun err(a: String) = System.err.println(a)

