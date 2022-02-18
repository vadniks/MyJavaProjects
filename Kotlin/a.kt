
import java.io.File
import java.io.FileInputStream
import java.util.Scanner
import java.util.zip.ZipInputStream
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

/*
 * <program> some.zip/tar
 * [<launchPath> | <archiveName>]% pwd
 * ...% ls
 * ...% cd <where>
 * ...% cat <what>
 */

const val tar = "tar"
const val zip = "zip"
const val ext = "exit"
const val pwd = "pwd"
const val ls = "ls"
const val cd = "cd"
const val cat = "cat"
const val dup = ".."
const val dt = '.'

const val spc = ' '

lateinit var arg: String
lateinit var arn: String
var crn: Nd? = null
lateinit var arf: File

data class Nd(
    var nm: String,
    var isd: Boolean,
    var prt: Nd?,
    val nds: ArrayList<Nd>,
    val bts: ByteArray?
) {

    fun isem() = nds.size == 0

    fun pth(): String {
        val rs = String()
        var pr = prt

        while (pr != null) {
            rs.plus(pr.nm + '/')
            pr = pr.prt
        }
        return rs
    }

    fun chl(a: String, b: Nd = this): Nd? {
        if (b.nm == a)
            return this

        for (i in b.nds)
            chl(a, i)

        return null
    }

    fun bds(pt: String, isd: Boolean, bts: ByteArray?) {
        val tks = pt.split(File.separator)

        var nd = this
//        if (tks.isEmpty() && tks.)
//            nds.add(Nd(pt, isd, this, ArrayList(), bts))
//        else {
            for ((j, i) in tks.withIndex()) {
                if (i.isEmpty())
                    continue

                val n = nd.chl(i)

                if (n != null)
                    nd = n
                else {
                    val a = j < tks.size - 1
                    nd.nds.add(Nd(tks[j], a, nd, ArrayList(), if (a) null else bts))
                }
            //}
        }
    }

    fun tvr(a: Nd = this, b: Int = 0) {
        for (i in 0..b)
            print("    ")

        println(a.nm)

        for (i in a.nds)
            tvr(i, b + 1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Nd

        return nm == other.nm
    }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("there must be only one launch argument")
        exitProcess(1)
    }
    println("_-archive viewer-_")

    arg = args[0]
    arn = arg.substringBefore(dt)
    arf = File(System.getProperty("user.dir"), arg)

    if (!arf.exists() or !arf.canRead()) {
        System.err.println("error accessing archive")
        exitProcess(1)
    }

    val sc = Scanner(System.`in`)
    var s: String
    while (true) {
        print(" % ")
        s = sc.nextLine()

        if (prs(s))
            break
    }
}

fun prs(s: String): Boolean {
    if (s == ext)
        return true

    pre()

    val tks = s.split(spc)
    when (tks[0]) {
        pwd -> println(crn!!.pth() + crn!!.nm)
        ls -> ls()
        cd -> cd(tks[1])
        cat -> cat(tks[1])
    }

    return false
}

fun pre() {
    crn = Nd(arn, true, null, ArrayList(), null)

    val fis = FileInputStream(arf)
    val zis = ZipInputStream(fis)
    var zen = zis.nextEntry
    while (zen != null) {
        //println("__ " + zen.name + " " + zen.isDirectory)
        crn!!.bds(zen.name, zen.isDirectory, zis.readAllBytes())
        //crn!!.nds.add(Nd(zen.name, zen.isDirectory, crn, ArrayList(), zis.readAllBytes()))
        zis.closeEntry()
        zen = zis.nextEntry
    }

    zis.closeEntry()
    zis.close()
    fis.close()
}
/*
fun untar(tis: TarInputStream, destFolder: String) {
    var dest: BufferedOutputStream? = null
    var entry: TarEntry?
    while (tis.nextEntry.also { entry = it } != null) {
        var count: Int
        val data = ByteArray(1024)
        if (entry!!.isDirectory) {
            File(destFolder + "/" + entry!!.name).mkdirs()
            continue
        } else {
            val di = entry!!.name.lastIndexOf('/')
            if (di != -1) {
                File(destFolder + "/" + entry!!.name.substring(0, di)).mkdirs()
            }
        }
        val fos = FileOutputStream(destFolder + "/" + entry!!.name)
        dest = BufferedOutputStream(fos)
        while (tis.read(data).also { count = it } != -1) {
            dest.write(data, 0, count)
        }
        dest.flush()
        dest.close()
    }
}
*/
fun ls() {
    for (i in crn!!.nds)
        println((if (i.isd) "<dir> " else "<file>") + "    " + i.nm)
}

fun cd(a: String) {
    val nd =
        if (a != dup)
            crn!!.chl(a)
        else
            crn!!.prt

    if (nd == null) {
        System.err.println("no such file or directory")
        return
    }

    crn = nd
}

fun cat(a: String) {
    val nd = crn!!.chl(a)
    if (nd == null) {
        System.err.println("error accessing file")
        return
    }

    if (nd.bts != null)
        print(String())
    else {
        println("file is empty")
    }
}

