package models.dao;


import java.util.UUID;
import models.Token;

/**
 * Token dao
 * 
 * @author Team RMG
 */
public interface ITokenDao extends IDao<Token, UUID> {

	
	
    /**
     * Retrieve a token by id and type.
     *
     * @param uuid token
     * @param type of token
     * @return Token reset token
     * @throws IllegalArgumentException
     */
    Token findByTokenAndType(String uuid, String type) throws IllegalArgumentException;
	
}
