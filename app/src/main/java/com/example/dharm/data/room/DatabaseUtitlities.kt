//package com.example.dharm.data.room
//
//import android.content.Context
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase? {
//        return AppDatabase.getDatabaseInstance(context)
//    }
//
//    @Provides
//    fun provideMyDao(database: AppDatabase): SavedChapterDao {
//        return database.savedChaptersDao()
//    }
//}
