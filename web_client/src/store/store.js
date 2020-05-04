import { createStore, applyMiddleware, compose, combineReducers } from 'redux';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import thunk from 'redux-thunk';
import authReducer from 'store/reducers/authReducer';
import restReducer from 'store/reducers/restReducer';

const composeEnhancers = (process.env.NODE_ENV === 'development' ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ : null) || compose;

const persistConfig = {
    key: 'root',
    storage: storage,
    whitelist: [
        'auth',
    ],
    blacklist: [
        'rest',
    ],
}

const rootReducer = combineReducers({
    auth: authReducer,
    rest: restReducer
});

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = createStore(persistedReducer, composeEnhancers(
    applyMiddleware(thunk),
));

export const persistor = persistStore(store);
