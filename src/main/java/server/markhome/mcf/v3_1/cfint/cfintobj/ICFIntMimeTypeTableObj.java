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

public interface ICFIntMimeTypeTableObj
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
	 *	Instantiate a new MimeType instance.
	 *
	 *	@return	A new instance.
	 */
	ICFIntMimeTypeObj newInstance();

	/**
	 *	Instantiate a new MimeType edition of the specified MimeType instance.
	 *
	 *	@return	A new edition.
	 */
	ICFIntMimeTypeEditObj newEditInstance( ICFIntMimeTypeObj orig );

	/**
	 *	Internal use only.
	 */
	ICFIntMimeTypeObj realiseMimeType( ICFIntMimeTypeObj Obj );

	/**
	 *	Internal use only.
	 */
	ICFIntMimeTypeObj createMimeType( ICFIntMimeTypeObj Obj );

	/**
	 *	Read a MimeType-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MimeType-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMimeTypeObj readMimeType( Integer pkey );

	/**
	 *	Read a MimeType-derived instance by it's primary key.
	 *
	 *	@param	pkey	The primary key identifying the instance to read.
	 *
	 *	@return	The MimeType-derived instance identified by the primary key,
	 *		or null if no such key value exists.
	 */
	ICFIntMimeTypeObj readMimeType( Integer pkey,
		boolean forceRead );

	ICFIntMimeTypeObj readCachedMimeType( Integer pkey );

	public void reallyDeepDisposeMimeType( ICFIntMimeTypeObj obj );

	void deepDisposeMimeType( Integer pkey );

	/**
	 *	Internal use only.
	 */
	ICFIntMimeTypeObj lockMimeType( Integer pkey );

	/**
	 *	Return a sorted list of all the MimeType-derived instances in the database.
	 *
	 *	@return	List of ICFIntMimeTypeObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMimeTypeObj> readAllMimeType();

	/**
	 *	Return a sorted map of all the MimeType-derived instances in the database.
	 *
	 *	@return	List of ICFIntMimeTypeObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	List<ICFIntMimeTypeObj> readAllMimeType( boolean forceRead );

	List<ICFIntMimeTypeObj> readCachedAllMimeType();

	/**
	 *	Get the CFIntMimeTypeObj instance for the primary key attributes.
	 *
	 *	@param	MimeTypeId	The MimeType key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMimeTypeObj cached instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMimeTypeObj readMimeTypeByIdIdx( int MimeTypeId );

	/**
	 *	Get the CFIntMimeTypeObj instance for the primary key attributes.
	 *
	 *	@param	MimeTypeId	The MimeType key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMimeTypeObj refreshed instance for the primary key, or
	 *		null if no such instance exists.
	 */
	ICFIntMimeTypeObj readMimeTypeByIdIdx( int MimeTypeId,
		boolean forceRead );

	/**
	 *	Get the CFIntMimeTypeObj instance for the unique UNameIdx key.
	 *
	 *	@param	Name	The MimeType key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMimeTypeObj cached instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMimeTypeObj readMimeTypeByUNameIdx(String Name );

	/**
	 *	Get the CFIntMimeTypeObj instance for the unique UNameIdx key.
	 *
	 *	@param	Name	The MimeType key attribute of the instance generating the id.
	 *
	 *	@return	CFIntMimeTypeObj refreshed instance for the unique UNameIdx key, or
	 *		null if no such instance exists.
	 */
	ICFIntMimeTypeObj readMimeTypeByUNameIdx(String Name,
		boolean forceRead );

	ICFIntMimeTypeObj readCachedMimeTypeByIdIdx( int MimeTypeId );

	ICFIntMimeTypeObj readCachedMimeTypeByUNameIdx( String Name );

	void deepDisposeMimeTypeByIdIdx( int MimeTypeId );

	void deepDisposeMimeTypeByUNameIdx( String Name );

	/**
	 *	Internal use only.
	 */
	ICFIntMimeTypeObj updateMimeType( ICFIntMimeTypeObj Obj );

	/**
	 *	Internal use only.
	 */
	void deleteMimeType( ICFIntMimeTypeObj Obj );

	/**
	 *	Internal use only.
	 *
	 *	@param	MimeTypeId	The MimeType key attribute of the instance generating the id.
	 */
	void deleteMimeTypeByIdIdx( int MimeTypeId );

	/**
	 *	Internal use only.
	 *
	 *	@param	Name	The MimeType key attribute of the instance generating the id.
	 */
	void deleteMimeTypeByUNameIdx(String Name );
}
