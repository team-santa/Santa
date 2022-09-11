export interface Decode {
  exp: number;
  role: string;
  sub: string;
}

export interface User {
  email: string;
  username: string;
  profileImageUrl: string;
}

export interface LocalUser {
  email: string;
  username: string;
  profileImageUrl: string;
  memberId: string;
}
