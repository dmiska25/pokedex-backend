package com.dylanmiska.pokemonAPI.services

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.FileNotFoundException

@Service
class ImageService {
    fun getImageByIDOrNull(id: Int): ClassPathResource? {
        try {
            return ClassPathResource("images/${id}.png")
        }
        catch (ex: FileNotFoundException) {
            return null
        }
    }
}