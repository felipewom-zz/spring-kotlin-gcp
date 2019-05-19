package br.com.felipewom.demo.controllers

import br.com.felipewom.demo.entities.Photo
import br.com.felipewom.demo.repositories.PhotoRepository
import io.swagger.annotations.ApiOperation
import org.springframework.cloud.gcp.storage.GoogleStorageResource
import org.springframework.context.ApplicationContext
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
class HelloController(val applicationContext: ApplicationContext,
                      val photoRepository: PhotoRepository) {
    private val prefix = "gs://empreend-me.appspot.com/demo"

    @ApiOperation(value = "Hello strange")
    @GetMapping("/hello")
    fun hello(name: String) = "Hello $name"

    @ApiOperation(value = "Upload image")
    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile): Photo {
        val id = UUID.randomUUID().toString()
        val uri = "$prefix/$id"
        val resource = applicationContext.getResource(uri) as GoogleStorageResource
        resource.outputStream.use {
            it.write(file.bytes)
        }
        return photoRepository.save(Photo(id, uri))
    }

    @ApiOperation(value = "Get image")
    @GetMapping("/image/{id}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun image(@PathVariable id: String): ResponseEntity<Resource> {
        val uri = "$prefix/$id"
        val resource = applicationContext.getResource(uri) as GoogleStorageResource
        return ResponseEntity.ok(resource)
    }

    @ApiOperation(value = "Delete image")
    @DeleteMapping("image/{id}")
    fun delete(@PathVariable id: String) {
        val uri = "$prefix/$id"
        val resource = applicationContext.getResource(uri) as GoogleStorageResource
        resource.blob.delete()
        photoRepository.deleteById(id)
    }
}