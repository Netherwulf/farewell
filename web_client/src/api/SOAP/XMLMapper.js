import * as xmljs from 'xml-js';

export const xmlToJs = (xml) => {
    return xmljs.xml2js(xml, { compact: true, spaces: 4 });
} 

export const createResults = (xml) => {
    const jsObject = xmlToJs(xml);
    return jsObject["soap:Envelope"]["soap:Body"]["CreateResponse"]["Results"];
}

export const retrieveResults = (xml) => {
    const jsObject = xmlToJs(xml);
    return jsObject["soap:Envelope"]["soap:Body"]["RetrieveResponseMsg"];
}