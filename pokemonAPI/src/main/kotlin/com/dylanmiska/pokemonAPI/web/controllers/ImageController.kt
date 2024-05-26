package com.dylanmiska.pokemonAPI.web.controllers

import com.dylanmiska.pokemonAPI.services.ImageService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/pokemon-api")
class ImageController(
    val service: ImageService
) {
    @GetMapping("/image/{id}")
    fun returnImageByID(@PathVariable id: Int): ResponseEntity<InputStreamResource?> =
        when(val it = service.getImageByIDOrNull(id)) {
            null -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon image associated with this id")
            else -> ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(InputStreamResource(it.inputStream))
        }
}