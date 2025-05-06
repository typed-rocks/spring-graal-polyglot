import {validate} from './lib.mjs';

const ZodResult = Java.type("chwoerz.ZodResult");

// person => Java Object != json
export function validatePerson(person) {
    // list of ZodIssues
    const result = validate({
        firstname: person.firstname(),
        lastname: person.lastname()
    });
    return result.map(entry => new ZodResult(entry.code));
}
