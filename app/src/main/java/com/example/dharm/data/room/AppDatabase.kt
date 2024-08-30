//package com.example.dharm.data.room
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//
//@Database(entities = [SavedChapters::class], version = 1, exportSchema = false)
//@TypeConverters(AppTypeConverter::class) // Use the correct converter class
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun savedChaptersDao(): SavedChapterDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabaseInstance(context: Context): AppDatabase? {
//            val tempInstance = INSTANCE
//            if (INSTANCE != null) {
//                return tempInstance
//            } else {
//                synchronized(this) {
//                    val roomDb =
//                        Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabse")
//                            .fallbackToDestructiveMigration().build()
//                    INSTANCE = roomDb
//                    return roomDb
//                }
//            }
//        }
//    }
//}
