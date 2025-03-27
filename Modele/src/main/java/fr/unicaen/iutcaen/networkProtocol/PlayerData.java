package fr.unicaen.iutcaen.networkProtocol;

import java.util.List;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.CellPack;
import fr.unicaen.iutcaen.model.factories.FactoryPlayer;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;

public class PlayerData extends ProtocolData {
	
	private EntityData cellsPackData; 
	private int id; 
	
	public PlayerData(Player player) {
		cellsPackData = new EntityData(player.getCells()); 
		id = player.getId(); 
	}
	
	public Player convertToPlayer() {
		Player player = FactoryPlayer.fabriquePlayer();
		
		player.setId(id);	
		player.setCellPack((CellPack) cellsPackData.convertToEntity());
		
		return player;
	}
	
}
