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

/*export const getFuneralDirectors = async () => {
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
}*/

export const getFuneralDirectors = async () => {
    const url = `http://${analyticalURL}/funeralDirectors`;
    const response = await get(url);
    return response ? response.funeralDirectors : [];
}

export const getFunerals = async () => {
  const url = `http://${reservationURL}/funerals`;
  const response = await get(url, 'application/xml');
  const json = convert.xml2json(response, {compact: true, spaces: 4});
  const jsonObj = JSON.parse(json);
  const dto = jsonObj.funeralListDTO;
  const funeralData = dto.funerals;
  const funerals = [];

  funeralData.forEach(element => {
    const funeralDirector = element.funeralDirector ? {
      dateOfBirth: element.funeralDirector.dateOfBirth._text,
      email: element.funeralDirector.email._text,
      id: element.funeralDirector.id._text,
      name: element.funeralDirector.name._text,
      surname: element.funeralDirector.surname._text,
      religion: element.funeralDirector.religion._text,
    } : null;
    
    const deceased = [];
    if (element.grave && element.grave.deceased) {
      const deceasedData = element.grave.deceased.length ? element.grave.deceased : [element.grave.deceased];
      deceasedData.forEach(p => {
        const person = {
          dateOfBirth: p.dateOfBirth._text,
          dateOfDeath: p.dateOfDeath._text,
          id: Number(p.id._text),
          name: p.name._text,
          surname: p.surname._text,
          graveId: Number(p.graveId._text),
          placeOfBirth: p.placeOfBirth._text,
          placeOfDeath: p.placeOfDeath._text
        };
        deceased.push(person);
      });
    }

    const grave = element.grave ? {
      id: Number(element.grave.id._text),
      reservationDate: element.grave.reservationDate ? element.grave.reservationDate._text : "",
      graveNumber: element.grave.graveNumber._text,
      coordinates: element.grave.coordinates._text,
      capacity: element.grave.capacity._text,
      funeralId: Number(element.grave.funeralId._text),
      userId: element.grave.userId ? Number(element.grave.userId._text) : "",
      deceased: deceased
    } : null;

    const funeral = {
      id: Number(element.id._text),
      reservationDate: element.reservationDate._text,
      date: element.date._text.substring(0,element.date._text.length-3),
      funeralDirectorId: element.funeralDirectorId._text,
      funeralDirector: funeralDirector,
      userId: element.userId._text,
      grave: grave
    };
    funerals.push(funeral);
  });
  return funerals;
}

export const getFuneralsJSON = async () => {
  const url = `http://${reservationURL}/funerals`;
  const response = {
    funerals: [
      {
        id: 1,
        reservationDate: '2012-05-03 22:15:10-00',
        date: '2012-06-12 10:15:00-00',
        funeralDirectorId: '1',
        funeralDirector: {
          id: 2,
          surname: 'Nowak',
          name: 'Jan',
          dateOfBirth: '1970-02-11',
          religion: 'protestantism',
          email: 'jacek@pwr.com'
        },
        userId: '25',
        grave: {
          id: 1,
          reservationDate: '2016-06-22 19:10:25-00',
          graveNumber: '99996',
          coordinates: '41-24-12.2-N 2-10-26.5-E',
          capacity: '4',
          funeralId: 1,
          userId: 40,
          deceased: [
            {
              id: 2,
              surname: 'Nowak',
              name: 'Rajmund',
              dateOfBirth: '1982-12-30',
              placeOfBirth: 'Nowa Sól',
              dateOfDeath: '2001-01-02',
              placeOfDeath: 'Wrocław',
              graveId: 1
            },
            {
              id: 1,
              surname: 'Kowalski',
              name: 'Jan',
              dateOfBirth: '1970-05-03',
              placeOfBirth: 'Warszawa',
              dateOfDeath: '2012-01-30',
              placeOfDeath: 'Gdynia',
              graveId: 1
            }
          ]
        }
      },
      {
        id: 2,
        reservationDate: '2007-07-17 23:22:31-00',
        date: '2007-08-23 12:45:00-00',
        funeralDirectorId: '1',
        funeralDirector: {
          id: 2,
          surname: 'Nowak',
          name: 'Jan',
          dateOfBirth: '1970-02-11',
          religion: 'protestantism',
          email: 'jacek@pwr.com'
        },
        userId: '26',
        grave: {
          id: 2,
          reservationDate: '2003-10-08 11:10:48-00',
          graveNumber: '99997',
          coordinates: '17-18-19.7-S 19-59-37.4-W',
          capacity: '3',
          funeralId: 2,
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
        }
      },
      {
        id: 3,
        reservationDate: '2014-05-13 17:29:39-00',
        date: '2014-07-22 15:30:00-00',
        funeralDirectorId: '2',
        funeralDirector: {
          id: 3,
          surname: 'Marecki',
          name: 'Paweł',
          dateOfBirth: '1971-02-10',
          religion: 'islam',
          email: 'sławomiro2.pl'
        },
        userId: '27',
        grave: null
      },
      {
        id: 4,
        reservationDate: '2019-10-12 14:11:29-00',
        date: '2019-10-15 14:10:00-00',
        funeralDirectorId: '4',
        funeralDirector: {
          id: 3,
          surname: 'Marecki',
          name: 'Paweł',
          dateOfBirth: '1971-02-10',
          religion: 'islam',
          email: 'sławomiro2.pl'
        },
        userId: '44',
        grave: null
      }
    ]
  };
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
          graveNumber: '100000',
          coordinates: '27-11-13.0-N 3-25-8.7-W',
          capacity: '7',
          userId: null,
          deceased: []
        },
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
        },
        {
          graveNumber: '99993',
          coordinates: '30-21-11.1-N 3-42-9.4-W',
          capacity: '1',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99992',
          coordinates: '30-21-11.1-N 3-42-8.4-W',
          capacity: '1',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99991',
          coordinates: '30-21-11.1-N 3-42-6.4-W',
          capacity: '3',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '99990',
          coordinates: '30-21-11.1-N 3-42-3.4-W',
          capacity: '2',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '97921',
          coordinates: '18-11-08.1-N 3-41-3.4-W',
          capacity: '6',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '96890',
          coordinates: '10-07-17.1-N 3-71-28.4-W',
          capacity: '5',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '91820',
          coordinates: '18-07-17.1-N 3-71-48.4-W',
          capacity: '3',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '90210',
          coordinates: '12-07-18.1-N 3-71-38.4-W',
          capacity: '11',
          userId: null,
          deceased: []
        },
        {
          graveNumber: '90211',
          coordinates: '10-07-18.1-N 3-71-38.3-W',
          capacity: '2',
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
    if (contentType) {
      config.headers["Content-Type"] = contentType;
    }
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