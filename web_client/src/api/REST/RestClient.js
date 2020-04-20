import axios from 'axios';
import * as util from 'api/REST/utility';

export const get = async (url, authToken) => {
    const config = util.getRequestConfig(authToken);
    return await axios.get(url, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        })
}

export const post = async (url, object, authToken) => {
    const config = util.getRequestConfig(authToken);
    config.headers["Content-Type"] = 'application/json';
    return await axios.post(url, object, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        })
}

export const put = async (url, object, authToken) => {
    const config = util.getRequestConfig(authToken);
    config.headers["Content-Type"] = 'application/json';
    return await axios.put(url, object, config)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.log('ERROR: ', error.response);
        })
}