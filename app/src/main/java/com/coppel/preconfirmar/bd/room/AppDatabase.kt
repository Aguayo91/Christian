package com.coppel.preconfirmar.bd.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coppel.preconfirmar.bd.dao.*
import com.coppel.preconfirmar.entities.*

@Database(

    entities = [
        SurtidoMueblesListEntity::class,
        SurtidoCelularesListEntity::class,
        SurtidoMensajeriaListEntity::class,
        SurtidoRopaListEntity::class,
        SurtidoSuministrosListEntity::class,
        SurtidoVentasXRListEntity::class,
        SurtidoMotosListEntity::class,
        IniciarSurtidoListEntity::class,
        ConsultarKeyXEntity::class,
        DetalleMasterListEntity::class,
        FaltantesListEntity::class,
        SinEtiqueta::class,
        Todo::class],
    version = 1, exportSchema = false
)


abstract class AppDatabase : RoomDatabase() {
    abstract fun mueblesDao(): MueblesDao
    abstract fun celularesDao(): CelularesDao
    abstract fun mensajeriaDao(): MensajeriaDao
    abstract fun ropaDao(): RopaDao
    abstract fun ventasxrDao(): VentasxrDao
    abstract fun suministrosDao(): SuministrosDao
    abstract fun motosDao(): MotosDao
    abstract fun iniciarSurtidoDao(): IniciarSurtidoDao
    abstract fun todoDao(): TodoDao
    abstract fun consultarkeyXDao(): ConsultarKeyXDao
    abstract fun consultarDetalleMasterDao(): DetalleMasterDao
    abstract fun faltantesDao():FaltantesDao
    abstract fun sinetiquetaDao():SinEtiquetaDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bd_coppel"
            ).build()
            return INSTANCE!!

        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}