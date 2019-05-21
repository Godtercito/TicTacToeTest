package es.codeurjc.ais.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class StatisticsService {
	
	private class Stats implements Comparable{
		
		private String player;
		private int wins, losses, tied;
		
		public Stats(Player player) {
			this.player = player.getName();
			wins = 0;
			losses = 0;
			tied = 0;
		}
		
		
		public String getPlayer() {
			return player;
		}
		public void setPlayer(String player) {
			this.player = player;
		}
		public int getWins() {
			return wins;
		}
		public void setWins(int wins) {
			this.wins = wins;
		}
		public int getLosses() {
			return losses;
		}
		public void setLosses(int losses) {
			this.losses = losses;
		}
		public int getTied() {
			return tied;
		}
		public void setTied(int tied) {
			this.tied = tied;
		}
		
		
		public boolean equals(Object o) {
			if (o==null)
				return false;
			if (this==o)
				return true;
			if (!(o instanceof Stats)) {
				return false;
			}
			Stats other = (Stats) o;
			return (this.getPlayer().equals(other.getPlayer()));
		}


		@Override
		public int compareTo(Object o) {
			Stats a = (Stats) o;
			int dif = this.getWins() - a.getWins();
			if (dif == 0) {
				dif = a.getLosses() - this.getLosses();
				if (dif == 0) {
					dif = this.getTied() - a.getTied();
				}
			}
			return -dif;
		}
		
	}

	
	private List<Stats> statistics;
	
	public StatisticsService() {
		statistics = new ArrayList<>();
	}
	
	private Stats addPlayer(Player p) {
		Stats newPlayer = new Stats(p);
		if (!(statistics.contains(newPlayer)))
			statistics.add(newPlayer);
		return statistics.get(statistics.indexOf(newPlayer)); 
	}
	
	public void addWin(Player p) {
		Stats winner = addPlayer(p);
		winner.setWins(winner.getWins()+1);
	}
	
	public void addLoss(Player p) {
		Stats losser = addPlayer(p);
		losser.setLosses(losser.getLosses()+1);
	}
	
	public void addTied(Player p) {
		Stats tier = addPlayer(p);
		tier.setTied(tier.getTied()+1);
	}
	
	public List<Stats> getStats(){
		Collections.sort(statistics);
		return statistics;
	}
}
