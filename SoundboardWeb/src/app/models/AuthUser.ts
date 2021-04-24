import {DiscordOauth2} from './DiscordOauth2';

export class AuthUser {
  constructor(
    public readonly discordUserId: string,
    public readonly discordOauth2: DiscordOauth2
  ) {
  }
}
