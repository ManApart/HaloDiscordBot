package rak.discord.haloCustom.commands.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import rak.discord.haloCustom.managers.GameStatsManager;
import rak.halo.stats.haloStats.model.enums.Platform;

public class LastGameStatsCommand  implements ICommand {

	private static final String INVOKE_TEXT = "lastGame";
	private static final String HELP = "USAGE: ~!lastGame <gamertag> <platform>"
			+ "\n(platform is either 'pc' or 'xbox'. Defaults to pc.)";
	
	@Override
	public boolean canBeCalled(String[] args, MessageReceivedEvent event) {
		boolean hasPermissions = event.getMember().getPermissions(event.getTextChannel()).contains(Permission.MANAGE_CHANNEL);
		return hasPermissions;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		GameStatsManager manager = new GameStatsManager();
		String gamertag = args.length > 0 ? args[0] : null;
		Platform platform = args.length > 1 ? Platform.findValue(args[1]) : null;
		
		String message = manager.getLastGameStatsMessage(gamertag, platform);
		
		//Split large messages
		if (message.length() > 2000){
			String[] lines = message.split("\n");
			String send = "";
			//Send the messages in smaller chunks
			for (int i=0; i< lines.length; i++){
				send += "\n" +lines[i];
				if (i % 5 == 0 || i == lines.length-1){
					event.getTextChannel().sendMessage(send).queue();
					send = "";
				}
			}
		} else{			
			event.getTextChannel().sendMessage(message).queue();
		}
	}

	@Override
	public String help() {
		return HELP;
	}
	
	@Override
	public String getInvokeText() {
		return INVOKE_TEXT;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		event.getTextChannel().deleteMessageById(event.getMessage().getId()).queue();
	}

}
