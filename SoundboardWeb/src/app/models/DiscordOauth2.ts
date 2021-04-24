export class DiscordOauth2 {
  constructor(
    public readonly accessToken: string,
    public readonly expiresIn: number,
    public readonly refreshToken: string
  ) {
  }
}
