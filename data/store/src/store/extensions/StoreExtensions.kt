package store.extensions

import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder

inline fun <Key : Any, Local : Any, Output : Any> storeBuilder(
    fetcher: Fetcher<Key, Local>,
    sourceOfTruth: SourceOfTruth<Key, Local, Output>,
): StoreBuilder<Key, Output> = StoreBuilder.from(fetcher, sourceOfTruth)
