package com.example.virt3

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File

@RestController
class Controller(private val repo: Repo) {

    private fun result(ok: Boolean) =
        ResponseEntity<Unit>(if (ok) HttpStatus.OK else HttpStatus.BAD_REQUEST)

    @PostMapping("/add/{id}")
    fun addElement(@PathVariable id: Int) =
        repo.save(Element(id)).also { result(true) }

    @GetMapping("/get")
    fun getElements() = StringBuilder().apply {
        for (i in repo.findAll()) {
            append(i.id)
            append(' ')
        }
    }.toString()

    @GetMapping(
        value = ["/emblem"],
        produces = [MediaType.IMAGE_PNG_VALUE]
    )
    @ResponseBody
    fun getEmblem() = File("/files/emblem.png").readBytes()
}
