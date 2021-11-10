package org.wit.photorally.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.photorally.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "photorally.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
                 .registerTypeAdapter(Uri::class.java, UriParser())
                 .create()
val listType: Type = object : TypeToken<ArrayList<PhotoRallyModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PhotoRallyJSONStore(private val context: Context) : PhotoRallyStore {

    var placemarks = mutableListOf<PhotoRallyModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PhotoRallyModel> {
        logAll()
        return placemarks
    }

    override fun create(placemark: PhotoRallyModel) {
        placemark.id = generateRandomId()
        placemarks.add(placemark)
        serialize()
    }


    override fun update(placemark: PhotoRallyModel) {
        val placemarksList = findAll() as ArrayList<PhotoRallyModel>
        var foundPlacemark: PhotoRallyModel? = placemarksList.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            foundPlacemark.image = placemark.image
            foundPlacemark.lat = placemark.lat
            foundPlacemark.lng = placemark.lng
            foundPlacemark.zoom = placemark.zoom
        }
        serialize()
    }

    override fun delete(placemark: PhotoRallyModel) {
        placemarks.remove(placemark)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(placemarks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        placemarks = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        placemarks.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}