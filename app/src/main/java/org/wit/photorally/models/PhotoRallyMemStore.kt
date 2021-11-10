package org.wit.photorally.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PhotoRallyMemStore : PhotoRallyStore {

    val placemarks = ArrayList<PhotoRallyModel>()

    override fun findAll(): List<PhotoRallyModel> {
        return placemarks
    }

    override fun create(placemark: PhotoRallyModel) {
        placemark.id = getId()
        placemarks.add(placemark)
        logAll()
    }

    override fun update(placemark: PhotoRallyModel) {
        val foundPlacemark: PhotoRallyModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
            logAll()
        }
    }

    override fun delete(placemark: PhotoRallyModel) {
        placemarks.remove(placemark)
    }

    private fun logAll() {
        placemarks.forEach { i("$it") }
    }
}