export interface ResponseAfterRegistration {
    nickname:string,
    password:string,
    email:string,
    role:string,
    _links: {
        self: {
            href:string
        }
    }
}
