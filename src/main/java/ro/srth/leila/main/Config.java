package ro.srth.leila.main;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.EnumSet;

public final class Config {

    public static final OnlineStatus STATUS = OnlineStatus.ONLINE;

    private static final String ACTIVITY_TEXT = "KICK ME IF ANIMAL ABUSER";
    public static final Activity ACTIVITY_STATUS = Activity.watching(ACTIVITY_TEXT);

    public static final MemberCachePolicy MEMBER_CACHE_POLICY = MemberCachePolicy.ALL;

    public static final EnumSet<CacheFlag> CACHE_FLAGS = EnumSet.of(CacheFlag.EMOJI, CacheFlag.VOICE_STATE);

    public static final EnumSet<GatewayIntent> GATEWAY_INTENTS = EnumSet.of(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES);

    private Config(){}
}
