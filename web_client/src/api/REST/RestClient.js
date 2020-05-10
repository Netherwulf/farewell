import axios from 'axios';
import * as util from 'api/REST/utility';
import { authURL, reservationURL } from 'common/const';

export const postUser = async (userObject) => {
    const url = `http://${authURL}/register`;
    const response = await post(url, userObject);
    return response;
}

export const getFunerals = async () => {
    const url = `http://${reservationURL}/funerals`;
    const response = await get(url);
    return response ? response.funerals : null;
}

export const post = async (url, object) => {
    const config = { headers: [] };
    config.headers["Content-Type"] = 'application/json';
    return await axios.post(url, object, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
            return error.response.data;
        });
}

export const get = async (url) => {
    const config = { headers: [] };
    config.headers["Content-Type"] = 'application/json';
    return await axios.get(url, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        });
}

export const getAuthenticated = async (url, authToken) => {
    const config = util.getRequestConfig(authToken);
    return await axios.get(url, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        });
}

export const postAuthenticated = async (url, object, authToken) => {
    const config = util.getRequestConfig(authToken);
    config.headers["Content-Type"] = 'application/json';
    return await axios.post(url, object, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        });
}

export const putAuthenticated = async (url, object, authToken) => {
    const config = util.getRequestConfig(authToken);
    config.headers["Content-Type"] = 'application/json';
    return await axios.put(url, object, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        });
}