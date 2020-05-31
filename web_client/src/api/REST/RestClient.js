import axios from 'axios';
import * as util from 'api/REST/utility';
import { authURL, reservationURL, analyticalURL } from 'common/const';

export const postUser = async (userObject) => {
    const url = `http://${authURL}/register`;
    const response = await post(url, userObject);
    return response;
}

export const getFuneralReport = async () => {
  const url = `http://${analyticalURL}/funeralReport`;
  const response = await get(url);
  return response;
}

export const getFuneralDirectors = async () => {
    const url = `http://${analyticalURL}/funeralDirectors`;
    const response = await get(url);
    return response ? response.funeralDirectors : [];
}

export const getFunerals = async () => {
  const url = `http://${reservationURL}/funerals`;
  const response = await get(url);
  return response ? response.funerals : [];
}

export const getGravesForUser = async (userId) => {
  const url = `http://${reservationURL}/users/${userId}/graves`;
  const response = await get(url);
  return response ? response.graves : [];
}

export const getGraves = async () => {
  const url = `http://${reservationURL}/graves`;
  const response = await get(url);
  return response ? response.graves : [];
}

export const postGrave = async (object) => {
  const url = `http://${reservationURL}/graves`;
  const response = await post(url, object);
  return response;
}

export const postFuneral = async (object) => {
  const url = `http://${reservationURL}/funerals`;
  const response = await post(url, object);
  return response;
}

export const getCemeteryGraves = async () => {
    const mockupData = [
        {
          graveNumber: '99996',
          coordinates: '41-24-12.2-N 2-10-26.5-E',
          capacity: '4',
          userId: 40,
          deceased: [
            {
              id: 1,
              surname: 'Nowak',
              name: 'Rajmund',
              dateOfBirth: '1982-12-30',
              placeOfBirth: 'Nowa Sól',
              dateOfDeath: '2001-01-02',
              placeOfDeath: 'Wrocław',
            },
            {
              id: 2,
              surname: 'Kowalski',
              name: 'Jan',
              dateOfBirth: '1970-05-03',
              placeOfBirth: 'Warszawa',
              dateOfDeath: '2012-01-30',
              placeOfDeath: 'Gdynia',
            }
          ]
        },
        {
          graveNumber: '99997',
          coordinates: '17-18-19.7-S 19-59-37.4-W',
          capacity: '3',
          userId: 41,
          deceased: [
            {
              id: 3,
              surname: 'Bałkański',
              name: 'Patryk',
              dateOfBirth: '1988-09-13',
              placeOfBirth: 'Dźwirzyno',
              dateOfDeath: '2010-03-08',
              placeOfDeath: 'Berlin',
              graveId: 2
            }
          ]
        },
        {
          graveNumber: '99998',
          coordinates: '30-22-16.0-N 3-25-27.7-W',
          capacity: '2',
          userId: 41,
          deceased: []
        },
        {
          graveNumber: '99997',
          coordinates: '30-21-18.0-N 3-26-22.7-W',
          capacity: '1',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99996',
          coordinates: '30-20-15.0-N 3-21-19.7-W',
          capacity: '4',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99995',
          coordinates: '30-22-16.0-N 3-21-16.4-W',
          capacity: '1',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99994',
          coordinates: '30-21-11.1-N 3-42-12.4-W',
          capacity: '1',
          userId: null,
          deceased: []
        }
    ];
    return mockupData;
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
            return error.response ? error.response.data : null;
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
            return error.response ? error.response.data : null;
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