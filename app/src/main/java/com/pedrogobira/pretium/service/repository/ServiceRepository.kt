package com.pedrogobira.pretium.service.repository

import android.content.ContentValues
import android.content.Context
import com.pedrogobira.pretium.service.constants.DatabaseConstants
import com.pedrogobira.pretium.service.model.ServiceModel

class ServiceRepository private constructor(context: Context) {

    private var serviceDatabaseHelper: ServiceDatabaseHelper = ServiceDatabaseHelper(context)

    companion object {
        private lateinit var repository: ServiceRepository

        fun getInstance(context: Context): ServiceRepository {
            if (!Companion::repository.isInitialized) {
                repository = ServiceRepository(context)
            }
            return repository
        }
    }

    fun get(id: Int): ServiceModel? {

        var service: ServiceModel? = null
        return try {
            val db = serviceDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION,
                DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR,
                DatabaseConstants.SERVICE.COLUMNS.HOURS,
                DatabaseConstants.SERVICE.COLUMNS.MINUTES,
                DatabaseConstants.SERVICE.COLUMNS.DAY,
                DatabaseConstants.SERVICE.COLUMNS.MONTH,
                DatabaseConstants.SERVICE.COLUMNS.YEAR
            )

            val selection = DatabaseConstants.SERVICE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DatabaseConstants.SERVICE.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val description =
                    cursor.getString(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION))
                val pricePerHour =
                    cursor.getDouble(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR))
                val hours =
                    cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.HOURS))
                val minutes =
                    cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MINUTES))
                val day =
                    cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DAY))
                val month =
                    cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MONTH))
                val year =
                    cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.YEAR))

                service =
                    ServiceModel(id, pricePerHour, hours, minutes, day, month, year, description)
            }

            cursor?.close()
            service
        } catch (e: Exception) {
            service
        }
    }

    fun save(service: ServiceModel): Boolean {
        return try {
            val db = serviceDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(
                DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR,
                service.pricePerHour
            )
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.HOURS, service.hours)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.MINUTES, service.minutes)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.DAY, service.day)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.MONTH, service.month)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.YEAR, service.year)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION, service.description)
            db.insert(DatabaseConstants.SERVICE.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<ServiceModel> {
        val list: MutableList<ServiceModel> = ArrayList()
        return try {
            val db = serviceDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.SERVICE.COLUMNS.ID,
                DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION,
                DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR,
                DatabaseConstants.SERVICE.COLUMNS.HOURS,
                DatabaseConstants.SERVICE.COLUMNS.MINUTES,
                DatabaseConstants.SERVICE.COLUMNS.DAY,
                DatabaseConstants.SERVICE.COLUMNS.MONTH,
                DatabaseConstants.SERVICE.COLUMNS.YEAR
            )

            val cursor = db.query(
                DatabaseConstants.SERVICE.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.ID))
                    val description =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION))
                    val pricePerHour =
                        cursor.getDouble(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR))
                    val hours =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.HOURS))
                    val minutes =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MINUTES))
                    val day =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DAY))
                    val month =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MONTH))
                    val year =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.YEAR))

                    val service = ServiceModel(
                        id,
                        pricePerHour,
                        hours,
                        minutes,
                        day,
                        month,
                        year,
                        description
                    )
                    list.add(service)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun getByMonthAndYear(month: Int, year: Int): List<ServiceModel> {
        val list: MutableList<ServiceModel> = ArrayList()
        return try {
            val db = serviceDatabaseHelper.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION,
                DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR,
                DatabaseConstants.SERVICE.COLUMNS.HOURS,
                DatabaseConstants.SERVICE.COLUMNS.MINUTES,
                DatabaseConstants.SERVICE.COLUMNS.DAY,
                DatabaseConstants.SERVICE.COLUMNS.MONTH,
                DatabaseConstants.SERVICE.COLUMNS.YEAR
            )

            val selection =
                DatabaseConstants.SERVICE.COLUMNS.MONTH + " = ? AND " + DatabaseConstants.SERVICE.COLUMNS.YEAR + " = ?"
            val args = arrayOf(month.toString(), year.toString())

            val cursor = db.query(
                DatabaseConstants.SERVICE.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.ID))
                    val description =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION))
                    val pricePerHour =
                        cursor.getDouble(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR))
                    val hours =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.HOURS))
                    val minutes =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MINUTES))
                    val day =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.DAY))
                    val month =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.MONTH))
                    val year =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.SERVICE.COLUMNS.YEAR))

                    val service = ServiceModel(
                        id,
                        pricePerHour,
                        hours,
                        minutes,
                        day,
                        month,
                        year,
                        description
                    )
                    list.add(service)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun update(service: ServiceModel): Boolean {
        return try {
            val db = serviceDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(
                DatabaseConstants.SERVICE.COLUMNS.PRICE_PER_HOUR,
                service.pricePerHour
            )
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.HOURS, service.hours)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.MINUTES, service.minutes)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.DAY, service.day)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.MONTH, service.month)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.YEAR, service.year)
            contentValues.put(DatabaseConstants.SERVICE.COLUMNS.DESCRIPTION, service.description)

            val selection = DatabaseConstants.SERVICE.COLUMNS.ID + " = ?"
            val args = arrayOf(service.id.toString())

            db.update(DatabaseConstants.SERVICE.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = serviceDatabaseHelper.writableDatabase
            val selection = DatabaseConstants.SERVICE.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.SERVICE.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }

}