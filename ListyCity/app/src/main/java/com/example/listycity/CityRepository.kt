package com.example.listycity

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CityRepository {
    //firestone database added
    private val db = Firebase.firestore
    private val citiesRef = db.collection("cities")

    private val _cities = mutableStateListOf<City>()

    val cities: List<City>
        get() = _cities

    init {
        citiesRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            _cities.clear()

            snapshot?.documents?.forEach { document ->
                val city = document.toObject(City::class.java)  //firestone doc to object for every doc
                if (city != null) {
                    _cities.add(city)
                }
            }
        }
    }

    fun addCity(city: City) {       //add city function added
        citiesRef.document(city.name).set(city)
    }
    fun updateCity(oldCity: City, updatedCity: City) {
        citiesRef.document(oldCity.name).set(updatedCity)
    }
    fun deleteCity(city: City) {    //delete city function added
        citiesRef.document(city.name).delete()
    }
}