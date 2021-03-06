package rak.discord.haloCustom.commands;

import java.util.ArrayList;
import java.util.HashMap;

import rak.discord.haloCustom.commands.command.ClearConsoleCommand;
import rak.discord.haloCustom.commands.command.HelpCommand;
import rak.discord.haloCustom.commands.command.ICommand;
import rak.discord.haloCustom.commands.command.LastGameStatsCommand;
import rak.discord.haloCustom.commands.command.PingCommand;
import rak.discord.haloCustom.commands.command.SetTopicGroupCommand;
import rak.discord.haloCustom.commands.command.TimmyTimeCommand;

public class CommandManager {
	private HashMap<String, ICommand> commands ;
	
	public CommandManager(){
		commands = populateCommands();
	}
	
	private HashMap<String, ICommand> populateCommands() {
		ArrayList<ICommand> commandList = new ArrayList<>();
		
		//TODO - add new Commands here until we have a more elegant solution
		commandList.add(new PingCommand());
		commandList.add(new SetTopicGroupCommand());
		commandList.add(new HelpCommand());
		commandList.add(new ClearConsoleCommand());
		commandList.add(new LastGameStatsCommand());
		commandList.add(new TimmyTimeCommand());
		
		HashMap<String, ICommand> commands = new HashMap<>();
		for (ICommand command : commandList){
			commands.put(command.getInvokeText().toLowerCase(), command);
		}
		return commands;
	}
	
	
	public void handleCommand(CommandContainer cmd){
		if (commands.containsKey(cmd.getInvoke())){
			ICommand command = commands.get(cmd.getInvoke());
			boolean safe = command.canBeCalled(cmd.getArgs(), cmd.getEvent());
			
			if (safe){
				command.action(cmd.getArgs(), cmd.getEvent());
			}
			command.executed(safe, cmd.getEvent());
		}
	}

	public ArrayList<ICommand> getAllCommands() {
		return new ArrayList<ICommand>(commands.values());
	}

}
