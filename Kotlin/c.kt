@file:JvmName("MainKt")
@file:Suppress("ConvertToStringTemplate", "SpellCheckingInspection")

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import LangParser.AtomContext as AtomContext

interface Expression {
    fun compile(): Any
}

abstract class Atom<T>(val value: T) : Expression, JSONObject()

class StringAtom(value: String) : Atom<String>(value) {
    override fun compile() = value
    init { put("String", value) }
}

class IntegerAtom(value: Int) : Atom<Int>(value) {
    override fun compile() = value
    init { put("Integer", value) }
}

class BooleanAtom(value: Boolean) : Atom<Boolean>(value) {
    override fun compile() = value
    init { put("Boolean", value) }
}

class NullAtom : Atom<Unit>(Unit) {
    override fun compile() = throw RuntimeException()
}

class EntryAtom(value: Array<Expression>) : Atom<Array<Expression>>(value) {
    override fun compile() = value.contentToString()

    init { compileJson(this) }

    private fun compileJson(atom: Expression) {
        when (atom) {
            is StringAtom -> atom.put("String", atom.value)
            is IntegerAtom -> atom.put("Integer", atom.value)
            is BooleanAtom -> atom.put("Boolean", atom.value)
            else -> {
                val jsonArr = JSONArray()
                for (i in (atom as EntryAtom).value) {
                    compileJson(i)
                    jsonArr.put(i)
                }
                atom.put("Array", jsonArr)
            }
        }
    }

    fun printContentAsTree(level: Int = -1, expression: Expression = this) {
        if (expression !is EntryAtom) {
            for (i in 0 until level)
                print("\t")

            println(expression.compile())
        } else {
            for (i in 0..level)
                print("\t")
            println("{")

            for (i in expression.value)
                printContentAsTree(level + 1, i)

            for (i in 0..level)
                print("\t")
            println("}")
        }
    }
}

class AtomVisiter : LangBaseVisitor<Expression>() {
    private var lastEntry: EntryAtom? = null
    private val declaredVariables = HashMap<String, String>()
    private val builtInFunctions = HashMap<String, (Any, Any) -> Any>()

    init {
        builtInFunctions["pls"] = { a, b -> a as Int; b as Int; a + b }
        //                 ^~~ plus function
        builtInFunctions["mns"] = { a, b -> a as Int; b as Int; a - b }
        //                 ^~~ minus function
    }

    // aka marcos
    private val AtomContext.booleanAtom             get() = this.BLN()
    private val AtomContext.integerAtom             get() = this.NUM()
    private val AtomContext.commentAtom             get() = this.CMT()
    private val AtomContext.stringAtom              get() = this.STR()
    private val AtomContext.variableDeclarationAtom get() = this.VDC()
    private val AtomContext.variableAtom            get() = this.VAR()
    private val AtomContext.functionAtom            get() = this.FUN()

    override fun visitAtom(atomContext: AtomContext): Expression? =
        if (atomContext.booleanAtom != null)
            BooleanAtom(atomContext.booleanAtom.text.toBoolean())

        else if (atomContext.integerAtom != null)
            IntegerAtom(atomContext.integerAtom.text.toInt())

        else if (atomContext.commentAtom != null)
            NullAtom()

        else if (atomContext.stringAtom != null) {
            val atomValue = atomContext.stringAtom.text
            StringAtom(atomValue.substring(1, atomValue.length - 1))

        } else if (atomContext.variableDeclarationAtom != null) {
            val atomValues = atomContext
                .variableDeclarationAtom
                .text
                .split('=')

            declaredVariables[atomValues[0]] = atomValues[1]
            NullAtom()

        } else if (atomContext.variableAtom != null) {
            val variableValue = declaredVariables[atomContext.VAR().text]!!

            when {
                variableValue ==
                        true.toString() ||
                        variableValue == false.toString()
                    -> BooleanAtom(variableValue.toBoolean())

                variableValue.contains("\"") ->
                    StringAtom(variableValue.substring(0, variableValue.length - 1))

                try { variableValue.toInt(); true }
                catch (ignnored: Exception) { false }
                    -> IntegerAtom(variableValue.toInt())

                else -> throw IllegalArgumentException()
            }
        } else if (atomContext.functionAtom != null) {
            val functionValue = atomContext.functionAtom.text

            var leftArgument = functionValue.substring(
                functionValue.indexOf('[') + 1,
                functionValue.indexOf(','))

            var rightArgument = functionValue.substring(
                functionValue.indexOf(',') + 1,
                functionValue.indexOf(']'))

            if (leftArgument.contains('$'))
                leftArgument = declaredVariables[leftArgument]!!

            if (rightArgument.contains('$'))
                rightArgument = declaredVariables[rightArgument]!!

            IntegerAtom(builtInFunctions[
                    functionValue.substring(1, functionValue.indexOf('['))
                ]?.let { it(leftArgument.toInt(), rightArgument.toInt()) } as Int)
        } else
            null

    override fun visitList(listContext: LangParser.ListContext?): Expression {
        val array = ArrayList<Expression>()
        for (item in listContext!!.item()) {
            val expression = visit(item) ?: continue
            if (expression !is NullAtom)
                array.add(expression)
        }
        return EntryAtom(array.toTypedArray()).apply { lastEntry = this }
    }

    override fun aggregateResult(aggregate: Expression?, nextResult: Expression?) =
        super.aggregateResult(aggregate, nextResult) ?: lastEntry
}

fun main(vararg arguments: String) {
    val languageFileContent = File(arguments[0]).readText()

    @Suppress("DEPRECATION")
    val inputStream = ANTLRInputStream(languageFileContent)

    val lexer = LangLexer(inputStream)
    val tokenStream = CommonTokenStream(lexer)
    val parser = LangParser(tokenStream)

    val visitor = AtomVisiter()

    val expression = visitor.visit(parser.exp())
    expression as EntryAtom

    val result = expression.toString()

    println("__begin_debug__")
    expression.printContentAsTree()
    println("__end_debug__\n")

    println(result)
}

