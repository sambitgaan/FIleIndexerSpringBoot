export class UserData {
    userId!: string;
    userName!: string;
    password!: string;
    firstName!: string;
    lastName!: string;
    token: string | undefined;
}

export class LoginData {
    userName!: string;
    password!: string;
}

export class LoginResponse {
    data!: UserData;
    message!: string;
    status!: string;
}