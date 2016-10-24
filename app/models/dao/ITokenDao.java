package models.dao;


import java.util.UUID;

import models.Token;
import models.Token.TypeToken;

/**
 * 
 * 
 * @author Team RMG
 */
public interface ITokenDao extends IDao<Token, UUID> {

	
	
    /**
     * Retrieve a token by id and type.
     *
     * @param token token Id
     * @param type  type of token
     * @return a resetToken
     */
    Token findByTokenAndType(String token, TypeToken type);
	
}
