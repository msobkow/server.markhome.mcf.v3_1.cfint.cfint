// Description: Java 25 Table Object interface for CFInt.

/*
 *	server.markhome.mcf.CFInt
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFInt - Internet Essentials
 *	
 *	This file is part of Mark's Code Fractal CFInt.
 *	
 *	Mark's Code Fractal CFInt is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU Library General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFInt is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU Library General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFInt is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU Library General Public License
 *	along with Mark's Code Fractal CFInt.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes in order to
 *	tie it to proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfint.cfintobj;

import java.math.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;

public interface ICFIntURLProtocolTableObj
{
	public ICFIntSchemaObj getSchema();
	public void setSchema( ICFIntSchemaObj value );

	public void minimizeMemory();

	public String getTableName();
	public String getTableDbName();

	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	public int getClassCode();

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	// public static int getBackingClassCode();

	Class getObjQualifyingClass();

	/**
	 *	Instantiate a new URLProtocol instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntURLProtocolObj newInstance();

	/**
	 *	Instantiate a new URLProtocol edition of the specified URLProtocol instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntURLProtocolEditObj newEditInstance( ICFIntURLProtocolObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntURLProtocolObj realiseURLProtocol( ICFIntURLProtocolObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntURLProtocolObj createURLProtocol( ICFIntURLProtocolObj Obj );

	/**
	 *	Read a URLProtocol-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The URLProtocol-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntURLProtocolObj readURLProtocol( Integer pkey );

	/**
	 *	Read a URLProtocol-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The URLProtocol-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntURLProtocolObj readURLProtocol( Integer pkey,
		boolean forceRead );

	ICFIntURLProtocolObj readCachedURLProtocol( Integer pkey );

	public void reallyDeepDisposeURLProtocol( ICFIntURLProtocolObj obj );

	void deepDisposeURLProtocol( Integer pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntURLProtocolObj lockURLProtocol( Integer pkey );

	/**
	 *	Return a sorted list of all the URLProtocol-derived instances in the database.
	 *
	 *	@return	List of ICFIntURLProtocolObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntURLProtocolObj> readAllURLProtocol();

	/**
	 *	Return a sorted map of all the URLProtocol-derived instances in the database.
	 *
	 *	@return	List of ICFIntURLProtocolObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntURLProtocolObj> readAllURLProtocol( boolean forceRead );

	List<ICFIntURLProtocolObj> readCachedAllURLProtocol();

	/**
	 *	Get the CFIntURLProtocolObj instance for the primary key attributes.
	 *
	 *	@param	URLProtocolId	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	CFIntURLProtocolObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntURLProtocolObj readURLProtocolByIdIdx( int URLProtocolId );

	/**
	 *	Get the CFIntURLProtocolObj instance for the primary key attributes.
	 *
	 *	@param	URLProtocolId	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	CFIntURLProtocolObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntURLProtocolObj readURLProtocolByIdIdx( int URLProtocolId,
		boolean forceRead );

	/**
	 *	Get the CFIntURLProtocolObj instance for the unique UNameIdx key.
	 *
	 *	@param	Name	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	CFIntURLProtocolObj cached instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntURLProtocolObj readURLProtocolByUNameIdx(String Name );

	/**
	 *	Get the CFIntURLProtocolObj instance for the unique UNameIdx key.
	 *
	 *	@param	Name	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	CFIntURLProtocolObj refreshed instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntURLProtocolObj readURLProtocolByUNameIdx(String Name,
		boolean forceRead );

	/**
	 *	Get the map of CFIntURLProtocolObj instances sorted by their primary keys for the duplicate IsSecureIdx key.
	 *
	 *	@param	IsSecure	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntURLProtocolObj cached instances sorted by their primary keys for the duplicate IsSecureIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntURLProtocolObj> readURLProtocolByIsSecureIdx( boolean IsSecure );

	/**
	 *	Get the map of CFIntURLProtocolObj instances sorted by their primary keys for the duplicate IsSecureIdx key.
	 *
	 *	@param	IsSecure	The URLProtocol key attribute of the instance generating the id.
	 *
	 *	@return	List of CFIntURLProtocolObj cached instances sorted by their primary keys for the duplicate IsSecureIdx key,
	 *		which may be an empty set.
	 */
	List<ICFIntURLProtocolObj> readURLProtocolByIsSecureIdx( boolean IsSecure,
		boolean forceRead );

	ICFIntURLProtocolObj readCachedURLProtocolByIdIdx( int URLProtocolId );

	ICFIntURLProtocolObj readCachedURLProtocolByUNameIdx( String Name );

	List<ICFIntURLProtocolObj> readCachedURLProtocolByIsSecureIdx( boolean IsSecure );

	void deepDisposeURLProtocolByIdIdx( int URLProtocolId );

	void deepDisposeURLProtocolByUNameIdx( String Name );

	void deepDisposeURLProtocolByIsSecureIdx( boolean IsSecure );

	/**
	 *	Internal use only.
	 */
	ICFIntURLProtocolObj updateURLProtocol( ICFIntURLProtocolObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteURLProtocol( ICFIntURLProtocolObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	URLProtocolId	The URLProtocol key attribute of the instance generating the id.
	 */
	void deleteURLProtocolByIdIdx( int URLProtocolId );

	/**
	 *	Internal use only.
	 *
	 *	@param	Name	The URLProtocol key attribute of the instance generating the id.
	 */
	void deleteURLProtocolByUNameIdx(String Name );

	/**
	 *	Internal use only.
	 *
	 *	@param	IsSecure	The URLProtocol key attribute of the instance generating the id.
	 */
	void deleteURLProtocolByIsSecureIdx( boolean IsSecure );
}
