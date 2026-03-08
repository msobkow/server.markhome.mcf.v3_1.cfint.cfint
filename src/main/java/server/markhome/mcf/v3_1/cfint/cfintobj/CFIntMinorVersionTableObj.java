// Description: Java 25 Table Object implementation for MinorVersion.

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

public class CFIntMinorVersionTableObj
	implements ICFIntMinorVersionTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntMinorVersion.CLASS_CODE;
	protected static final int backingClassCode = ICFIntMinorVersion.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> members;
	private Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> allMinorVersion;
	private Map< ICFIntMinorVersionByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > > indexByTenantIdx;
	private Map< ICFIntMinorVersionByMajorVerIdxKey,
		Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > > indexByMajorVerIdx;
	private Map< ICFIntMinorVersionByNameIdxKey,
		ICFIntMinorVersionObj > indexByNameIdx;
	public static String TABLE_NAME = "MinorVersion";
	public static String TABLE_DBNAME = "mnvrdef";

	public CFIntMinorVersionTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntMinorVersionObj>();
		allMinorVersion = null;
		indexByTenantIdx = null;
		indexByMajorVerIdx = null;
		indexByNameIdx = null;
	}

	public CFIntMinorVersionTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntMinorVersionObj>();
		allMinorVersion = null;
		indexByTenantIdx = null;
		indexByMajorVerIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntMinorVersionTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntMinorVersionTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		return( ICFIntTenantObj.class );
	}


	@Override
	public void minimizeMemory() {
		allMinorVersion = null;
		indexByTenantIdx = null;
		indexByMajorVerIdx = null;
		indexByNameIdx = null;
		List<ICFIntMinorVersionObj> toForget = new LinkedList<ICFIntMinorVersionObj>();
		ICFIntMinorVersionObj cur = null;
		Iterator<ICFIntMinorVersionObj> iter = members.values().iterator();
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
	 *	CFIntMinorVersionObj.
	 */
	@Override
	public ICFIntMinorVersionObj newInstance() {
		ICFIntMinorVersionObj inst = new CFIntMinorVersionObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntMinorVersionObj.
	 */
	@Override
	public ICFIntMinorVersionEditObj newEditInstance( ICFIntMinorVersionObj orig ) {
		ICFIntMinorVersionEditObj edit = new CFIntMinorVersionEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntMinorVersionObj realiseMinorVersion( ICFIntMinorVersionObj Obj ) {
		ICFIntMinorVersionObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntMinorVersionObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntMinorVersionObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntMinorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByMajorVerIdx != null ) {
				ICFIntMinorVersionByMajorVerIdxKey keyMajorVerIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
				keyMajorVerIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapMajorVerIdx = indexByMajorVerIdx.get( keyMajorVerIdx );
				if( mapMajorVerIdx != null ) {
					mapMajorVerIdx.remove( keepObj.getPKey() );
					if( mapMajorVerIdx.size() <= 0 ) {
						indexByMajorVerIdx.remove( keyMajorVerIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMinorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntMinorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByMajorVerIdx != null ) {
				ICFIntMinorVersionByMajorVerIdxKey keyMajorVerIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
				keyMajorVerIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapMajorVerIdx = indexByMajorVerIdx.get( keyMajorVerIdx );
				if( mapMajorVerIdx != null ) {
					mapMajorVerIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMinorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allMinorVersion != null ) {
				allMinorVersion.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allMinorVersion != null ) {
				allMinorVersion.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntMinorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByMajorVerIdx != null ) {
				ICFIntMinorVersionByMajorVerIdxKey keyMajorVerIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
				keyMajorVerIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				Map<CFLibDbKeyHash256, ICFIntMinorVersionObj > mapMajorVerIdx = indexByMajorVerIdx.get( keyMajorVerIdx );
				if( mapMajorVerIdx != null ) {
					mapMajorVerIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMinorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredMajorVersionId( keepObj.getRequiredMajorVersionId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntMinorVersionObj createMinorVersion( ICFIntMinorVersionObj Obj ) {
		ICFIntMinorVersionObj obj = Obj;
		ICFIntMinorVersion rec = obj.getMinorVersionRec();
		schema.getCFIntBackingStore().getTableMinorVersion().createMinorVersion(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersion( CFLibDbKeyHash256 pkey ) {
		return( readMinorVersion( pkey, false ) );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersion( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntMinorVersionObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntMinorVersion readRec = schema.getCFIntBackingStore().getTableMinorVersion().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getMinorVersionTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntMinorVersionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMinorVersionObj readCachedMinorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMinorVersionObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeMinorVersion( ICFIntMinorVersionObj obj )
	{
		final String S_ProcName = "CFIntMinorVersionTableObj.reallyDeepDisposeMinorVersion() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntMinorVersionObj existing = readCachedMinorVersion( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntMinorVersionByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntMinorVersionByMajorVerIdxKey keyMajorVerIdx = schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
		keyMajorVerIdx.setRequiredMajorVersionId( existing.getRequiredMajorVersionId() );

		ICFIntMinorVersionByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
		keyNameIdx.setRequiredMajorVersionId( existing.getRequiredMajorVersionId() );
		keyNameIdx.setRequiredName( existing.getRequiredName() );



		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByMajorVerIdx != null ) {
			if( indexByMajorVerIdx.containsKey( keyMajorVerIdx ) ) {
				indexByMajorVerIdx.get( keyMajorVerIdx ).remove( pkey );
				if( indexByMajorVerIdx.get( keyMajorVerIdx ).size() <= 0 ) {
					indexByMajorVerIdx.remove( keyMajorVerIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeMinorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMinorVersionObj obj = readCachedMinorVersion( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMinorVersionObj lockMinorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMinorVersionObj locked = null;
		ICFIntMinorVersion lockRec = schema.getCFIntBackingStore().getTableMinorVersion().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getMinorVersionTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntMinorVersionObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockMinorVersion", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntMinorVersionObj> readAllMinorVersion() {
		return( readAllMinorVersion( false ) );
	}

	@Override
	public List<ICFIntMinorVersionObj> readAllMinorVersion( boolean forceRead ) {
		final String S_ProcName = "readAllMinorVersion";
		if( ( allMinorVersion == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> map = new HashMap<CFLibDbKeyHash256,ICFIntMinorVersionObj>();
			allMinorVersion = map;
			ICFIntMinorVersion[] recList = schema.getCFIntBackingStore().getTableMinorVersion().readAllDerived( null );
			ICFIntMinorVersion rec;
			ICFIntMinorVersionObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMinorVersionObj realised = (ICFIntMinorVersionObj)obj.realise();
			}
		}
		int len = allMinorVersion.size();
		ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
		Iterator<ICFIntMinorVersionObj> valIter = allMinorVersion.values().iterator();
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
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			@Override
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntMinorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntMinorVersionObj> readCachedAllMinorVersion() {
		final String S_ProcName = "readCachedAllMinorVersion";
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>();
		if( allMinorVersion != null ) {
			int len = allMinorVersion.size();
			ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
			Iterator<ICFIntMinorVersionObj> valIter = allMinorVersion.values().iterator();
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
		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readMinorVersionByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersionByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntMinorVersionObj obj = readMinorVersion( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntMinorVersionObj> readMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readMinorVersionByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntMinorVersionObj> readMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readMinorVersionByTenantIdx";
		ICFIntMinorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntMinorVersionByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMinorVersionObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntMinorVersionObj>();
			ICFIntMinorVersionObj obj;
			ICFIntMinorVersion[] recList = schema.getCFIntBackingStore().getTableMinorVersion().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntMinorVersion rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getMinorVersionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMinorVersionObj realised = (ICFIntMinorVersionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
		Iterator<ICFIntMinorVersionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			@Override
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntMinorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntMinorVersionObj> readMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId )
	{
		return( readMinorVersionByMajorVerIdx( MajorVersionId,
			false ) );
	}

	@Override
	public List<ICFIntMinorVersionObj> readMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId,
		boolean forceRead )
	{
		final String S_ProcName = "readMinorVersionByMajorVerIdx";
		ICFIntMinorVersionByMajorVerIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict;
		if( indexByMajorVerIdx == null ) {
			indexByMajorVerIdx = new HashMap< ICFIntMinorVersionByMajorVerIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMinorVersionObj > >();
		}
		if( ( ! forceRead ) && indexByMajorVerIdx.containsKey( key ) ) {
			dict = indexByMajorVerIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntMinorVersionObj>();
			ICFIntMinorVersionObj obj;
			ICFIntMinorVersion[] recList = schema.getCFIntBackingStore().getTableMinorVersion().readDerivedByMajorVerIdx( null,
				MajorVersionId );
			ICFIntMinorVersion rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getMinorVersionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMinorVersionObj realised = (ICFIntMinorVersionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByMajorVerIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
		Iterator<ICFIntMinorVersionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			@Override
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFIntMinorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name )
	{
		return( readMinorVersionByNameIdx( MajorVersionId,
			Name,
			false ) );
	}

	@Override
	public ICFIntMinorVersionObj readMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntMinorVersionByNameIdxKey,
				ICFIntMinorVersionObj >();
		}
		ICFIntMinorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		key.setRequiredName( Name );
		ICFIntMinorVersionObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntMinorVersion rec = schema.getCFIntBackingStore().getTableMinorVersion().readDerivedByNameIdx( null,
				MajorVersionId,
				Name );
			if( rec != null ) {
				obj = schema.getMinorVersionTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntMinorVersionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMinorVersionObj readCachedMinorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMinorVersionObj obj = null;
		obj = readCachedMinorVersion( Id );
		return( obj );
	}

	@Override
	public List<ICFIntMinorVersionObj> readCachedMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedMinorVersionByTenantIdx";
		ICFIntMinorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
				Iterator<ICFIntMinorVersionObj> valIter = dict.values().iterator();
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
		}
		else {
			ICFIntMinorVersionObj obj;
			Iterator<ICFIntMinorVersionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			@Override
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public List<ICFIntMinorVersionObj> readCachedMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId )
	{
		final String S_ProcName = "readCachedMinorVersionByMajorVerIdx";
		ICFIntMinorVersionByMajorVerIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		ArrayList<ICFIntMinorVersionObj> arrayList = new ArrayList<ICFIntMinorVersionObj>();
		if( indexByMajorVerIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict;
			if( indexByMajorVerIdx.containsKey( key ) ) {
				dict = indexByMajorVerIdx.get( key );
				int len = dict.size();
				ICFIntMinorVersionObj arr[] = new ICFIntMinorVersionObj[len];
				Iterator<ICFIntMinorVersionObj> valIter = dict.values().iterator();
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
		}
		else {
			ICFIntMinorVersionObj obj;
			Iterator<ICFIntMinorVersionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntMinorVersionObj> cmp = new Comparator<ICFIntMinorVersionObj>() {
			@Override
			public int compare( ICFIntMinorVersionObj lhs, ICFIntMinorVersionObj rhs ) {
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
					CFLibDbKeyHash256 lhsPKey = lhs.getPKey();
					CFLibDbKeyHash256 rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFIntMinorVersionObj readCachedMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name )
	{
		ICFIntMinorVersionObj obj = null;
		ICFIntMinorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntMinorVersionObj> valIter = members.values().iterator();
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
			Iterator<ICFIntMinorVersionObj> valIter = members.values().iterator();
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
	public void deepDisposeMinorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMinorVersionObj obj = readCachedMinorVersionByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeMinorVersionByTenantIdx";
		ICFIntMinorVersionObj obj;
		List<ICFIntMinorVersionObj> arrayList = readCachedMinorVersionByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntMinorVersionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId )
	{
		final String S_ProcName = "deepDisposeMinorVersionByMajorVerIdx";
		ICFIntMinorVersionObj obj;
		List<ICFIntMinorVersionObj> arrayList = readCachedMinorVersionByMajorVerIdx( MajorVersionId );
		if( arrayList != null )  {
			Iterator<ICFIntMinorVersionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name )
	{
		ICFIntMinorVersionObj obj = readCachedMinorVersionByNameIdx( MajorVersionId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMinorVersionObj updateMinorVersion( ICFIntMinorVersionObj Obj ) {
		ICFIntMinorVersionObj obj = Obj;
		schema.getCFIntBackingStore().getTableMinorVersion().updateMinorVersion( null,
			Obj.getMinorVersionRec() );
		obj = (ICFIntMinorVersionObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteMinorVersion( ICFIntMinorVersionObj Obj ) {
		ICFIntMinorVersionObj obj = Obj;
		schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersion( null,
			obj.getMinorVersionRec() );
		Obj.forget();
	}

	@Override
	public void deleteMinorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMinorVersionObj obj = readMinorVersion(Id);
		if( obj != null ) {
			ICFIntMinorVersionEditObj editObj = (ICFIntMinorVersionEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntMinorVersionEditObj)obj.beginEdit();
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
		deepDisposeMinorVersionByIdIdx( Id );
	}

	@Override
	public void deleteMinorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntMinorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntMinorVersionByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMinorVersionObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByTenantIdx( null,
				TenantId );
			Iterator<ICFIntMinorVersionObj> iter = dict.values().iterator();
			ICFIntMinorVersionObj obj;
			List<ICFIntMinorVersionObj> toForget = new LinkedList<ICFIntMinorVersionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTenantIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByTenantIdx( null,
				TenantId );
		}
		deepDisposeMinorVersionByTenantIdx( TenantId );
	}

	@Override
	public void deleteMinorVersionByMajorVerIdx( CFLibDbKeyHash256 MajorVersionId )
	{
		ICFIntMinorVersionByMajorVerIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByMajorVerIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		if( indexByMajorVerIdx == null ) {
			indexByMajorVerIdx = new HashMap< ICFIntMinorVersionByMajorVerIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMinorVersionObj > >();
		}
		if( indexByMajorVerIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntMinorVersionObj> dict = indexByMajorVerIdx.get( key );
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByMajorVerIdx( null,
				MajorVersionId );
			Iterator<ICFIntMinorVersionObj> iter = dict.values().iterator();
			ICFIntMinorVersionObj obj;
			List<ICFIntMinorVersionObj> toForget = new LinkedList<ICFIntMinorVersionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByMajorVerIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByMajorVerIdx( null,
				MajorVersionId );
		}
		deepDisposeMinorVersionByMajorVerIdx( MajorVersionId );
	}

	@Override
	public void deleteMinorVersionByNameIdx( CFLibDbKeyHash256 MajorVersionId,
		String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntMinorVersionByNameIdxKey,
				ICFIntMinorVersionObj >();
		}
		ICFIntMinorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMinorVersion().newByNameIdxKey();
		key.setRequiredMajorVersionId( MajorVersionId );
		key.setRequiredName( Name );
		ICFIntMinorVersionObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByNameIdx( null,
				MajorVersionId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableMinorVersion().deleteMinorVersionByNameIdx( null,
				MajorVersionId,
				Name );
		}
		deepDisposeMinorVersionByNameIdx( MajorVersionId,
				Name );
	}
}