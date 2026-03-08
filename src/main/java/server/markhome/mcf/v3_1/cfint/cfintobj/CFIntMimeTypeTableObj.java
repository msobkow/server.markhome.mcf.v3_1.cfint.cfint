// Description: Java 25 Table Object implementation for MimeType.

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

public class CFIntMimeTypeTableObj
	implements ICFIntMimeTypeTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntMimeType.CLASS_CODE;
	protected static final int backingClassCode = ICFIntMimeType.CLASS_CODE;
	private Map<Integer, ICFIntMimeTypeObj> members;
	private Map<Integer, ICFIntMimeTypeObj> allMimeType;
	private Map< ICFIntMimeTypeByUNameIdxKey,
		ICFIntMimeTypeObj > indexByUNameIdx;
	public static String TABLE_NAME = "MimeType";
	public static String TABLE_DBNAME = "mimetype";

	public CFIntMimeTypeTableObj() {
		schema = null;
		members = new HashMap<Integer, ICFIntMimeTypeObj>();
		allMimeType = null;
		indexByUNameIdx = null;
	}

	public CFIntMimeTypeTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<Integer, ICFIntMimeTypeObj>();
		allMimeType = null;
		indexByUNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntMimeTypeTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( backingClassCode );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( runtimeClassCode );
	}

	/**
	 *	Set the runtime class code for this table; this is done only during application initialization by the SchemaObj's <tt>initClassCodes()</tt> static method,
	 *	which will only set the class codes once and never again.  Once set, the class codes are immutable within the application.
	 *	Application programmers should never invoke this method, so it has package access only.
	 *
	 *	@param	argNewClassCode	The runtime class code to be used by clients and integrated application logic to identify this table of this schema.
	 */
	static void setRuntimeClassCode(int argNewClassCode ) {
		if (argNewClassCode <= 0) {
			throw new CFLibArgumentUnderflowException(CFIntMimeTypeTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
		}
		runtimeClassCode = argNewClassCode;
	}

	@Override
	public ICFIntSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFIntSchemaObj value ) {
		schema = (ICFIntSchemaObj)value;
	}

	@Override
	public String getTableName() {
		return( TABLE_NAME );
	}

	@Override
	public String getTableDbName() {
		return( TABLE_DBNAME );
	}

	@Override
	public Class getObjQualifyingClass() {
		return( null );
	}


	@Override
	public void minimizeMemory() {
		allMimeType = null;
		indexByUNameIdx = null;
		List<ICFIntMimeTypeObj> toForget = new LinkedList<ICFIntMimeTypeObj>();
		ICFIntMimeTypeObj cur = null;
		Iterator<ICFIntMimeTypeObj> iter = members.values().iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			toForget.add( cur );
		}
		iter = toForget.iterator();
		while( iter.hasNext() ) {
			cur = iter.next();
			cur.forget();
		}
	}
	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntMimeTypeObj.
	 */
	@Override
	public ICFIntMimeTypeObj newInstance() {
		ICFIntMimeTypeObj inst = new CFIntMimeTypeObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntMimeTypeObj.
	 */
	@Override
	public ICFIntMimeTypeEditObj newEditInstance( ICFIntMimeTypeObj orig ) {
		ICFIntMimeTypeEditObj edit = new CFIntMimeTypeEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntMimeTypeObj realiseMimeType( ICFIntMimeTypeObj Obj ) {
		ICFIntMimeTypeObj obj = Obj;
		Integer pkey = obj.getPKey();
		ICFIntMimeTypeObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntMimeTypeObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByUNameIdx != null ) {
				ICFIntMimeTypeByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.remove( keyUNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByUNameIdx != null ) {
				ICFIntMimeTypeByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

			if( allMimeType != null ) {
				allMimeType.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allMimeType != null ) {
				allMimeType.put( keepObj.getPKey(), keepObj );
			}

			if( indexByUNameIdx != null ) {
				ICFIntMimeTypeByUNameIdxKey keyUNameIdx =
					schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
				keyUNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByUNameIdx.put( keyUNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntMimeTypeObj createMimeType( ICFIntMimeTypeObj Obj ) {
		ICFIntMimeTypeObj obj = Obj;
		ICFIntMimeType rec = obj.getMimeTypeRec();
		schema.getCFIntBackingStore().getTableMimeType().createMimeType(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntMimeTypeObj readMimeType( Integer pkey ) {
		return( readMimeType( pkey, false ) );
	}

	@Override
	public ICFIntMimeTypeObj readMimeType( Integer pkey, boolean forceRead ) {
		ICFIntMimeTypeObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntMimeType readRec = schema.getCFIntBackingStore().getTableMimeType().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getMimeTypeTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntMimeTypeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMimeTypeObj readCachedMimeType( Integer pkey ) {
		ICFIntMimeTypeObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeMimeType( ICFIntMimeTypeObj obj )
	{
		final String S_ProcName = "CFIntMimeTypeTableObj.reallyDeepDisposeMimeType() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		Integer pkey = obj.getPKey();
		ICFIntMimeTypeObj existing = readCachedMimeType( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntMimeTypeByUNameIdxKey keyUNameIdx = schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
		keyUNameIdx.setRequiredName( existing.getRequiredName() );



		if( indexByUNameIdx != null ) {
			indexByUNameIdx.remove( keyUNameIdx );
		}


	}
	@Override
	public void deepDisposeMimeType( Integer pkey ) {
		ICFIntMimeTypeObj obj = readCachedMimeType( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMimeTypeObj lockMimeType( Integer pkey ) {
		ICFIntMimeTypeObj locked = null;
		ICFIntMimeType lockRec = schema.getCFIntBackingStore().getTableMimeType().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getMimeTypeTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntMimeTypeObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockMimeType", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntMimeTypeObj> readAllMimeType() {
		return( readAllMimeType( false ) );
	}

	@Override
	public List<ICFIntMimeTypeObj> readAllMimeType( boolean forceRead ) {
		final String S_ProcName = "readAllMimeType";
		if( ( allMimeType == null ) || forceRead ) {
			Map<Integer, ICFIntMimeTypeObj> map = new HashMap<Integer,ICFIntMimeTypeObj>();
			allMimeType = map;
			ICFIntMimeType[] recList = schema.getCFIntBackingStore().getTableMimeType().readAllDerived( null );
			ICFIntMimeType rec;
			ICFIntMimeTypeObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMimeTypeObj realised = (ICFIntMimeTypeObj)obj.realise();
			}
		}
		int len = allMimeType.size();
		ICFIntMimeTypeObj arr[] = new ICFIntMimeTypeObj[len];
		Iterator<ICFIntMimeTypeObj> valIter = allMimeType.values().iterator();
		int idx = 0;
		while( ( idx < len ) && valIter.hasNext() ) {
			arr[idx++] = valIter.next();
		}
		if( idx < len ) {
			throw new CFLibArgumentUnderflowException( getClass(),
				S_ProcName,
				0,
				"idx",
				idx,
				len );
		}
		else if( valIter.hasNext() ) {
			throw new CFLibArgumentOverflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
		}
		ArrayList<ICFIntMimeTypeObj> arrayList = new ArrayList<ICFIntMimeTypeObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMimeTypeObj> cmp = new Comparator<ICFIntMimeTypeObj>() {
			@Override
			public int compare( ICFIntMimeTypeObj lhs, ICFIntMimeTypeObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntMimeTypeObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntMimeTypeObj> readCachedAllMimeType() {
		final String S_ProcName = "readCachedAllMimeType";
		ArrayList<ICFIntMimeTypeObj> arrayList = new ArrayList<ICFIntMimeTypeObj>();
		if( allMimeType != null ) {
			int len = allMimeType.size();
			ICFIntMimeTypeObj arr[] = new ICFIntMimeTypeObj[len];
			Iterator<ICFIntMimeTypeObj> valIter = allMimeType.values().iterator();
			int idx = 0;
			while( ( idx < len ) && valIter.hasNext() ) {
				arr[idx++] = valIter.next();
			}
			if( idx < len ) {
				throw new CFLibArgumentUnderflowException( getClass(),
					S_ProcName,
					0,
					"idx",
					idx,
					len );
			}
			else if( valIter.hasNext() ) {
				throw new CFLibArgumentOverflowException( getClass(),
						S_ProcName,
						0,
						"idx",
						idx,
						len );
			}
			for( idx = 0; idx < len; idx ++ ) {
				arrayList.add( arr[idx] );
			}
		}
		Comparator<ICFIntMimeTypeObj> cmp = new Comparator<ICFIntMimeTypeObj>() {
			public int compare( ICFIntMimeTypeObj lhs, ICFIntMimeTypeObj rhs ) {
				if( lhs == null ) {
					if( rhs == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhs == null ) {
					return( 1 );
				}
				else {
					Integer lhsPKey = lhs.getPKey();
					Integer rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFIntMimeTypeObj readMimeTypeByIdIdx( int MimeTypeId )
	{
		return( readMimeTypeByIdIdx( MimeTypeId,
			false ) );
	}

	@Override
	public ICFIntMimeTypeObj readMimeTypeByIdIdx( int MimeTypeId, boolean forceRead )
	{
		ICFIntMimeTypeObj obj = readMimeType( MimeTypeId, forceRead );
		return( obj );
	}

	@Override
	public ICFIntMimeTypeObj readMimeTypeByUNameIdx( String Name )
	{
		return( readMimeTypeByUNameIdx( Name,
			false ) );
	}

	@Override
	public ICFIntMimeTypeObj readMimeTypeByUNameIdx( String Name, boolean forceRead )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntMimeTypeByUNameIdxKey,
				ICFIntMimeTypeObj >();
		}
		ICFIntMimeTypeByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
		key.setRequiredName( Name );
		ICFIntMimeTypeObj obj = null;
		if( ( ! forceRead ) && indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
		}
		else {
			ICFIntMimeType rec = schema.getCFIntBackingStore().getTableMimeType().readDerivedByUNameIdx( null,
				Name );
			if( rec != null ) {
				obj = schema.getMimeTypeTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntMimeTypeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMimeTypeObj readCachedMimeTypeByIdIdx( int MimeTypeId )
	{
		ICFIntMimeTypeObj obj = null;
		obj = readCachedMimeType( MimeTypeId );
		return( obj );
	}

	@Override
	public ICFIntMimeTypeObj readCachedMimeTypeByUNameIdx( String Name )
	{
		ICFIntMimeTypeObj obj = null;
		ICFIntMimeTypeByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
		key.setRequiredName( Name );
		if( indexByUNameIdx != null ) {
			if( indexByUNameIdx.containsKey( key ) ) {
				obj = indexByUNameIdx.get( key );
			}
			else {
				Iterator<ICFIntMimeTypeObj> valIter = members.values().iterator();
				while( ( obj == null ) && valIter.hasNext() ) {
					obj = valIter.next();
					if( obj != null ) {
						if( obj.getRec().compareTo( key ) != 0 ) {
							obj = null;
						}
					}
				}
			}
		}
		else {
			Iterator<ICFIntMimeTypeObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) != 0 ) {
						obj = null;
					}
				}
			}
		}
		return( obj );
	}

	@Override
	public void deepDisposeMimeTypeByIdIdx( int MimeTypeId )
	{
		ICFIntMimeTypeObj obj = readCachedMimeTypeByIdIdx( MimeTypeId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeMimeTypeByUNameIdx( String Name )
	{
		ICFIntMimeTypeObj obj = readCachedMimeTypeByUNameIdx( Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMimeTypeObj updateMimeType( ICFIntMimeTypeObj Obj ) {
		ICFIntMimeTypeObj obj = Obj;
		schema.getCFIntBackingStore().getTableMimeType().updateMimeType( null,
			Obj.getMimeTypeRec() );
		obj = (ICFIntMimeTypeObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteMimeType( ICFIntMimeTypeObj Obj ) {
		ICFIntMimeTypeObj obj = Obj;
		schema.getCFIntBackingStore().getTableMimeType().deleteMimeType( null,
			obj.getMimeTypeRec() );
		Obj.forget();
	}

	@Override
	public void deleteMimeTypeByIdIdx( int MimeTypeId )
	{
		ICFIntMimeTypeObj obj = readMimeType(MimeTypeId);
		if( obj != null ) {
			ICFIntMimeTypeEditObj editObj = (ICFIntMimeTypeEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntMimeTypeEditObj)obj.beginEdit();
				if( editObj != null ) {
					editStarted = true;
				}
				else {
					editStarted = false;
				}
			}
			else {
				editStarted = false;
			}
			if( editObj != null ) {
				editObj.deleteInstance();
				if( editStarted ) {
					editObj.endEdit();
				}
			}
			obj.forget();
		}
		deepDisposeMimeTypeByIdIdx( MimeTypeId );
	}

	@Override
	public void deleteMimeTypeByUNameIdx( String Name )
	{
		if( indexByUNameIdx == null ) {
			indexByUNameIdx = new HashMap< ICFIntMimeTypeByUNameIdxKey,
				ICFIntMimeTypeObj >();
		}
		ICFIntMimeTypeByUNameIdxKey key = schema.getCFIntBackingStore().getFactoryMimeType().newByUNameIdxKey();
		key.setRequiredName( Name );
		ICFIntMimeTypeObj obj = null;
		if( indexByUNameIdx.containsKey( key ) ) {
			obj = indexByUNameIdx.get( key );
			schema.getCFIntBackingStore().getTableMimeType().deleteMimeTypeByUNameIdx( null,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableMimeType().deleteMimeTypeByUNameIdx( null,
				Name );
		}
		deepDisposeMimeTypeByUNameIdx( Name );
	}
}