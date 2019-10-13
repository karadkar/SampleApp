package io.github.karadkar.sample.db

import io.github.karadkar.sample.main.LocationListItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import java.io.Closeable

class LocationDao(private val realm: Realm = Realm.getDefaultInstance()) : Closeable {

    fun <T : RealmModel> RealmResults<T>.asLoadedFlowableList(): Flowable<List<T>> {
        return this.asFlowable()
            .filter { it.isLoaded } // check if async query is completed
            .map { it } // maps to list
    }

    /**
     * Real Transaction callback in Rx Completable
     */
    fun Realm.completableTransaction(
        transactionBlock: (realm: Realm) -> Unit
    ): Completable {
        return Completable.create { completable ->
            this.executeTransactionAsync(
                { transactionRealm -> transactionBlock(transactionRealm) },
                { completable.onComplete() },
                { throwable -> completable.onError(throwable) }
            )
        }
    }

    fun save(result: LocationResult): Completable {
        return realm.completableTransaction {
            it.copyToRealmOrUpdate(result)
        }
    }

    fun getLocations(): Flowable<List<LocationListItem>> {
        return realm.where(LocationEntity::class.java)
            .findAllAsync().asLoadedFlowableList().map { entities: List<LocationEntity> ->
                return@map entities.mapTo(mutableListOf(), { entity ->
                    LocationListItem(
                        title = entity.place,
                        date = entity.dateString,
                        image = entity.imageUrl,
                        isFavourite = entity.isFavourite
                    )
                })
            }
    }

    fun getCustomerName(): String {
        return realm.where(LocationResult::class.java)
            .findFirst()?.customerName ?: "User"
    }

    fun toggleFavourite(place: String): Completable {
        return realm.completableTransaction {
            it.where(LocationEntity::class.java)
                .equalTo(LocationEntityFields.PLACE, place)
                .findFirst()?.let { item ->
                    item.isFavourite = !item.isFavourite
                }
        }
    }

    override fun close() {
        realm.close()
    }
}