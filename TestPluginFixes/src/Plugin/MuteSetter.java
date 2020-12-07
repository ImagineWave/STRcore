package Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import Plugin.MessageManager.MessageType;

public class MuteSetter implements CommandExecutor{
	private Main plugin;

	public MuteSetter(Main plugin) {
		this.plugin = plugin;	
		}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		 if (!(sender instanceof Player)) {
	            sender.sendMessage("Only for player usage!");
	            return true;
	        }
		 Player p = (Player) sender;
		 if (!sender.hasPermission("str.tempmute")) {
			 MessageManager.getManager().msg(p, MessageType.BAD, "� ��� ��� ���� ��� ������������� ������� tempmute");
			 return true;
		 }
		 if(args.length == 0) {
				MessageManager.getManager().msg(p, MessageType.INFO, "�������������: /tempmute <player> <time (������)> <�������>");
				return true;
		 }
		 if(args.length == 1) {
				MessageManager.getManager().msg(p, MessageType.INFO, "�������������: /tempmute <player> <time (������)> <�������>");
				return true;
		 }
		 if(args.length >= 2) {
			 Player t = (Bukkit.getPlayerExact(args[0]));
			 String message = "";
				for (int i = 2; i != args.length; i++) {
					message += args[i] +" ";
				}
			 try {
			 Long time = Long.parseLong(args[1]);
			 if( (time> 3600) && (!p.hasPermission("str.tempmute.ulimited"))){
				 MessageManager.getManager().msg(p, MessageType.BAD, "�� �� ������ ������ ����� ��� �� ���");
				 return true;
			 }
			 Long qtime = System.currentTimeMillis() + time*1000;
			 String reason = message;
			 long hours = time/3600;
			 long minutes = time%3600/60;
			 long seconds = time%3600%60;
			 String sthours = Long.toString(hours);
			 String stminutes = Long.toString(minutes);
			 String stseconds = Long.toString(seconds);
			 String msgduration = "";
			 if(hours != 0) msgduration += sthours + " ���(��) ";
			 if(minutes != 0) msgduration += stminutes + " �����(�) ";
			 if(seconds != 0) msgduration += stseconds + " ������(�) ";
			 Bukkit.broadcastMessage("�7[�c���������7]: �6"+ p.getName()+" �b������� ������ �6"+t.getName()+"�b �� �6" + msgduration+ "�b �� ������� �6" + message);
			 MessageManager.getManager().msg(t, MessageType.BAD, "��� ������� �6"+p.getName()+"�c �� �6" + msgduration+ "�c �� ������� �6" + message);
			playertomute(t,qtime,reason);
			 }
			 catch (NumberFormatException e) {
				 MessageManager.getManager().msg(p, MessageType.BAD, "������� ����� � ��������");
				 return true;
			 }
			// Bukkit.broadcastMessage("�7[�c���������7]: �6"+ p.getName()+" �b������� ������ �6"+t.getName()+"�b �� �6" + args[1]+ "�b ������ �� ������� �6" + message);
			 //MessageManager.getManager().msg(t, MessageType.BAD, "��� ������� �6"+p.getName()+"�c �� �6" + args[1]+ "�c ������ �� ������� �6" + message);
		 }
		return false;
	}
	public void playertomute (Player p, long time, String reason) {
		 String duration = Long.toString(time);
		 File muted = new File(plugin.getDataFolder() + File.separator + "muted.yml");
		 FileConfiguration m = YamlConfiguration.loadConfiguration(muted);
		 List<String> list = m.getStringList("users");
		 if(list.contains(p.getName()))
			{
				int index = list.indexOf(p.getName());
				int time1 = index + 1;
				int res = index + 2;
				list.remove(res);
				list.remove(time1);
				list.remove(index);
			}
		 list.add(p.getName());
		 list.add(duration);
		 list.add(reason);
		m.set("users",list);
				try {
			m.save(muted);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
}
