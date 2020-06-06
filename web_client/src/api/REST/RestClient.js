import axios from 'axios';
import * as util from 'api/REST/utility';
import { authURL, reservationURL, analyticalURL } from 'common/const';

var html2json = require('html2json').html2json;
var convert = require('xml-js');

export const postUser = async (userObject) => {
    const url = `http://${authURL}/register`;
    const response = await post(url, userObject);
    return response;
}

const getValueFromDiv = (body, name) => {
  const value = body.filter(obj => obj.attr).filter(obj => obj.attr.name === name)[0].child.filter(obj => obj.tag === "p")[0].child[0].text;
  return Number(value);
}

export const getGraveReport = async () => {
  const url = `http://${analyticalURL}/graveReport`;
  const response = await get(url, 'text/html');
  const html = response.substring(response.indexOf("<html>"));
  const jsonObj = html2json(html);
  const body = jsonObj.child[0].child[3].child;
  //console.log(body);
  const lst = body.filter(obj => obj.attr).filter(obj => obj.attr.name === "gravesPerUser")[0].child;
  const ul = lst.filter(obj => obj.tag === "ul")[0].child;
  const li = ul.filter(obj => obj.tag === "li");
  const arrGravesPerUser = li.map(object=>Number(object.child[0].text));
  //console.log(arrGravesPerUser);
  const lstz = body.filter(obj => obj.attr).filter(obj => obj.attr.name === "deceasedPerGrave")[0].child;
  const ulz = lstz.filter(obj => obj.tag === "ul")[0].child;
  const liz = ulz.filter(obj => obj.tag === "li");
  const arrDeceasedPerGrave = liz.map(object=>Number(object.child[0].text));
  //console.log(arrDeceasedPerGrave);
  const report = { 
    averageReservationToPurchaseTime: getValueFromDiv(body, "averageReservationToPurchaseTime"), 
    averageGravesPerDay: getValueFromDiv(body, "averageGravesPerDay"), 
    medianGravesPerDay: getValueFromDiv(body, "medianGravesPerDay"), 
    modeGravesPerDay: getValueFromDiv(body, "modeGravesPerDay"), 
    averageGravesPerMonth: getValueFromDiv(body, "averageGravesPerMonth"), 
    medianGravesPerMonth: getValueFromDiv(body, "medianGravesPerMonth"), 
    modeGravesPerMonth: getValueFromDiv(body, "modeGravesPerMonth"), 
    averageGravesPerYear: getValueFromDiv(body, "averageGravesPerYear"), 
    medianGravesPerYear: getValueFromDiv(body, "medianGravesPerYear"), 
    modeGravesPerYear: getValueFromDiv(body, "modeGravesPerYear"), 
    averageDeceasedPerGrave: getValueFromDiv(body, "averageDeceasedPerGrave"), 
    medianDeceasedPerGrave: getValueFromDiv(body, "medianDeceasedPerGrave"), 
    modeDeceasedPerGrave: getValueFromDiv(body, "modeDeceasedPerGrave"), 
    averageGravesPerUser: getValueFromDiv(body, "averageGravesPerUser"), 
    medianGravesPerUser: getValueFromDiv(body, "medianGravesPerUser"), 
    modeGravesPerUser: getValueFromDiv(body, "modeGravesPerUser"), 
    gravesPerUser: arrGravesPerUser,
    deceasedPerGrave: arrDeceasedPerGrave,
  };
  //console.log(report);
  return report;
}

export const getFuneralReport = async () => {
  const url = `http://${analyticalURL}/funeralReport`;
  const response = await get(url);
  //console.log(response);
  return response;
}

export const getFuneralDirectors = async () => {
    const url = `http://${analyticalURL}/funeralDirectors`;
    const response = await get(url, 'application/xml');
    const json = convert.xml2json(response, {compact: true, spaces: 4});
    const jsonObj = JSON.parse(json);
    const dto = jsonObj.FuneralDirectorListDTO;
    const funeralDirectorsData = dto.funeralDirectors.funeralDirectors;
    const funeralDirectors = [];
    funeralDirectorsData.forEach(element => {
      const funeralDirector = { 
        dateOfBirth: element.dateOfBirth._text,
        email: element.email._text,
        id: element.id._text,
        name: element.name._text,
        surname: element.surname._text,
        religion: element.religion._text,
      };
      funeralDirectors.push(funeralDirector);
    });
    return funeralDirectors.length ? funeralDirectors : [];
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

export const get = async (url, contentType) => {
    const config = { headers: [] };
    config.headers["Content-Type"] = 'application/json';
    if (contentType)
      config.headers["Content-Type"] = contentType;
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