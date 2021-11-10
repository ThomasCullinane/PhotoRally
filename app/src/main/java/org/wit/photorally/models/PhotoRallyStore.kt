package org.wit.photorally.models

interface PhotoRallyStore {
    fun findAll(): List<PhotoRallyModel>
    fun create(placemark: PhotoRallyModel)
    fun update(placemark: PhotoRallyModel)
    fun delete(placemark: PhotoRallyModel)
}