// Description: Java 25 Table Object implementation for MajorVersion.

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

public class CFIntMajorVersionTableObj
	implements ICFIntMajorVersionTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntMajorVersion.CLASS_CODE;
	protected static final int backingClassCode = ICFIntMajorVersion.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> members;
	private Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> allMajorVersion;
	private Map< ICFIntMajorVersionByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > > indexByTenantIdx;
	private Map< ICFIntMajorVersionBySubProjectIdxKey,
		Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > > indexBySubProjectIdx;
	private Map< ICFIntMajorVersionByNameIdxKey,
		ICFIntMajorVersionObj > indexByNameIdx;
	public static String TABLE_NAME = "MajorVersion";
	public static String TABLE_DBNAME = "mjvrdef";

	public CFIntMajorVersionTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntMajorVersionObj>();
		allMajorVersion = null;
		indexByTenantIdx = null;
		indexBySubProjectIdx = null;
		indexByNameIdx = null;
	}

	public CFIntMajorVersionTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntMajorVersionObj>();
		allMajorVersion = null;
		indexByTenantIdx = null;
		indexBySubProjectIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntMajorVersionTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntMajorVersionTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		allMajorVersion = null;
		indexByTenantIdx = null;
		indexBySubProjectIdx = null;
		indexByNameIdx = null;
		List<ICFIntMajorVersionObj> toForget = new LinkedList<ICFIntMajorVersionObj>();
		ICFIntMajorVersionObj cur = null;
		Iterator<ICFIntMajorVersionObj> iter = members.values().iterator();
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
	 *	CFIntMajorVersionObj.
	 */
	@Override
	public ICFIntMajorVersionObj newInstance() {
		ICFIntMajorVersionObj inst = new CFIntMajorVersionObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntMajorVersionObj.
	 */
	@Override
	public ICFIntMajorVersionEditObj newEditInstance( ICFIntMajorVersionObj orig ) {
		ICFIntMajorVersionEditObj edit = new CFIntMajorVersionEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntMajorVersionObj realiseMajorVersion( ICFIntMajorVersionObj Obj ) {
		ICFIntMajorVersionObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntMajorVersionObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntMajorVersionObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntMajorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexBySubProjectIdx != null ) {
				ICFIntMajorVersionBySubProjectIdxKey keySubProjectIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
				keySubProjectIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapSubProjectIdx = indexBySubProjectIdx.get( keySubProjectIdx );
				if( mapSubProjectIdx != null ) {
					mapSubProjectIdx.remove( keepObj.getPKey() );
					if( mapSubProjectIdx.size() <= 0 ) {
						indexBySubProjectIdx.remove( keySubProjectIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMajorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntMajorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySubProjectIdx != null ) {
				ICFIntMajorVersionBySubProjectIdxKey keySubProjectIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
				keySubProjectIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapSubProjectIdx = indexBySubProjectIdx.get( keySubProjectIdx );
				if( mapSubProjectIdx != null ) {
					mapSubProjectIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMajorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allMajorVersion != null ) {
				allMajorVersion.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allMajorVersion != null ) {
				allMajorVersion.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntMajorVersionByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexBySubProjectIdx != null ) {
				ICFIntMajorVersionBySubProjectIdxKey keySubProjectIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
				keySubProjectIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				Map<CFLibDbKeyHash256, ICFIntMajorVersionObj > mapSubProjectIdx = indexBySubProjectIdx.get( keySubProjectIdx );
				if( mapSubProjectIdx != null ) {
					mapSubProjectIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntMajorVersionByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
				keyNameIdx.setRequiredSubProjectId( keepObj.getRequiredSubProjectId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntMajorVersionObj createMajorVersion( ICFIntMajorVersionObj Obj ) {
		ICFIntMajorVersionObj obj = Obj;
		ICFIntMajorVersion rec = obj.getMajorVersionRec();
		schema.getCFIntBackingStore().getTableMajorVersion().createMajorVersion(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntMajorVersionObj readMajorVersion( CFLibDbKeyHash256 pkey ) {
		return( readMajorVersion( pkey, false ) );
	}

	@Override
	public ICFIntMajorVersionObj readMajorVersion( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntMajorVersionObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntMajorVersion readRec = schema.getCFIntBackingStore().getTableMajorVersion().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getMajorVersionTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntMajorVersionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMajorVersionObj readCachedMajorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMajorVersionObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeMajorVersion( ICFIntMajorVersionObj obj )
	{
		final String S_ProcName = "CFIntMajorVersionTableObj.reallyDeepDisposeMajorVersion() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntMajorVersionObj existing = readCachedMajorVersion( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntMajorVersionByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntMajorVersionBySubProjectIdxKey keySubProjectIdx = schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
		keySubProjectIdx.setRequiredSubProjectId( existing.getRequiredSubProjectId() );

		ICFIntMajorVersionByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
		keyNameIdx.setRequiredSubProjectId( existing.getRequiredSubProjectId() );
		keyNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getMinorVersionTableObj().deepDisposeMinorVersionByMajorVerIdx( existing.getRequiredId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexBySubProjectIdx != null ) {
			if( indexBySubProjectIdx.containsKey( keySubProjectIdx ) ) {
				indexBySubProjectIdx.get( keySubProjectIdx ).remove( pkey );
				if( indexBySubProjectIdx.get( keySubProjectIdx ).size() <= 0 ) {
					indexBySubProjectIdx.remove( keySubProjectIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeMajorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMajorVersionObj obj = readCachedMajorVersion( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMajorVersionObj lockMajorVersion( CFLibDbKeyHash256 pkey ) {
		ICFIntMajorVersionObj locked = null;
		ICFIntMajorVersion lockRec = schema.getCFIntBackingStore().getTableMajorVersion().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getMajorVersionTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntMajorVersionObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockMajorVersion", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntMajorVersionObj> readAllMajorVersion() {
		return( readAllMajorVersion( false ) );
	}

	@Override
	public List<ICFIntMajorVersionObj> readAllMajorVersion( boolean forceRead ) {
		final String S_ProcName = "readAllMajorVersion";
		if( ( allMajorVersion == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> map = new HashMap<CFLibDbKeyHash256,ICFIntMajorVersionObj>();
			allMajorVersion = map;
			ICFIntMajorVersion[] recList = schema.getCFIntBackingStore().getTableMajorVersion().readAllDerived( null );
			ICFIntMajorVersion rec;
			ICFIntMajorVersionObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMajorVersionObj realised = (ICFIntMajorVersionObj)obj.realise();
			}
		}
		int len = allMajorVersion.size();
		ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
		Iterator<ICFIntMajorVersionObj> valIter = allMajorVersion.values().iterator();
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
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			@Override
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
		List<ICFIntMajorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntMajorVersionObj> readCachedAllMajorVersion() {
		final String S_ProcName = "readCachedAllMajorVersion";
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>();
		if( allMajorVersion != null ) {
			int len = allMajorVersion.size();
			ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
			Iterator<ICFIntMajorVersionObj> valIter = allMajorVersion.values().iterator();
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
		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
	public ICFIntMajorVersionObj readMajorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readMajorVersionByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntMajorVersionObj readMajorVersionByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntMajorVersionObj obj = readMajorVersion( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntMajorVersionObj> readMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readMajorVersionByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntMajorVersionObj> readMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readMajorVersionByTenantIdx";
		ICFIntMajorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntMajorVersionByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMajorVersionObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntMajorVersionObj>();
			ICFIntMajorVersionObj obj;
			ICFIntMajorVersion[] recList = schema.getCFIntBackingStore().getTableMajorVersion().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntMajorVersion rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getMajorVersionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMajorVersionObj realised = (ICFIntMajorVersionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
		Iterator<ICFIntMajorVersionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			@Override
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
		List<ICFIntMajorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntMajorVersionObj> readMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId )
	{
		return( readMajorVersionBySubProjectIdx( SubProjectId,
			false ) );
	}

	@Override
	public List<ICFIntMajorVersionObj> readMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId,
		boolean forceRead )
	{
		final String S_ProcName = "readMajorVersionBySubProjectIdx";
		ICFIntMajorVersionBySubProjectIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict;
		if( indexBySubProjectIdx == null ) {
			indexBySubProjectIdx = new HashMap< ICFIntMajorVersionBySubProjectIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMajorVersionObj > >();
		}
		if( ( ! forceRead ) && indexBySubProjectIdx.containsKey( key ) ) {
			dict = indexBySubProjectIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntMajorVersionObj>();
			ICFIntMajorVersionObj obj;
			ICFIntMajorVersion[] recList = schema.getCFIntBackingStore().getTableMajorVersion().readDerivedBySubProjectIdx( null,
				SubProjectId );
			ICFIntMajorVersion rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getMajorVersionTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntMajorVersionObj realised = (ICFIntMajorVersionObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexBySubProjectIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
		Iterator<ICFIntMajorVersionObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			@Override
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
		List<ICFIntMajorVersionObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntMajorVersionObj readMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name )
	{
		return( readMajorVersionByNameIdx( SubProjectId,
			Name,
			false ) );
	}

	@Override
	public ICFIntMajorVersionObj readMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntMajorVersionByNameIdxKey,
				ICFIntMajorVersionObj >();
		}
		ICFIntMajorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		key.setRequiredName( Name );
		ICFIntMajorVersionObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntMajorVersion rec = schema.getCFIntBackingStore().getTableMajorVersion().readDerivedByNameIdx( null,
				SubProjectId,
				Name );
			if( rec != null ) {
				obj = schema.getMajorVersionTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntMajorVersionObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntMajorVersionObj readCachedMajorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMajorVersionObj obj = null;
		obj = readCachedMajorVersion( Id );
		return( obj );
	}

	@Override
	public List<ICFIntMajorVersionObj> readCachedMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedMajorVersionByTenantIdx";
		ICFIntMajorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
				Iterator<ICFIntMajorVersionObj> valIter = dict.values().iterator();
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
			ICFIntMajorVersionObj obj;
			Iterator<ICFIntMajorVersionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			@Override
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
	public List<ICFIntMajorVersionObj> readCachedMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId )
	{
		final String S_ProcName = "readCachedMajorVersionBySubProjectIdx";
		ICFIntMajorVersionBySubProjectIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		ArrayList<ICFIntMajorVersionObj> arrayList = new ArrayList<ICFIntMajorVersionObj>();
		if( indexBySubProjectIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict;
			if( indexBySubProjectIdx.containsKey( key ) ) {
				dict = indexBySubProjectIdx.get( key );
				int len = dict.size();
				ICFIntMajorVersionObj arr[] = new ICFIntMajorVersionObj[len];
				Iterator<ICFIntMajorVersionObj> valIter = dict.values().iterator();
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
			ICFIntMajorVersionObj obj;
			Iterator<ICFIntMajorVersionObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntMajorVersionObj> cmp = new Comparator<ICFIntMajorVersionObj>() {
			@Override
			public int compare( ICFIntMajorVersionObj lhs, ICFIntMajorVersionObj rhs ) {
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
	public ICFIntMajorVersionObj readCachedMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name )
	{
		ICFIntMajorVersionObj obj = null;
		ICFIntMajorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntMajorVersionObj> valIter = members.values().iterator();
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
			Iterator<ICFIntMajorVersionObj> valIter = members.values().iterator();
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
	public void deepDisposeMajorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMajorVersionObj obj = readCachedMajorVersionByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeMajorVersionByTenantIdx";
		ICFIntMajorVersionObj obj;
		List<ICFIntMajorVersionObj> arrayList = readCachedMajorVersionByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntMajorVersionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId )
	{
		final String S_ProcName = "deepDisposeMajorVersionBySubProjectIdx";
		ICFIntMajorVersionObj obj;
		List<ICFIntMajorVersionObj> arrayList = readCachedMajorVersionBySubProjectIdx( SubProjectId );
		if( arrayList != null )  {
			Iterator<ICFIntMajorVersionObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name )
	{
		ICFIntMajorVersionObj obj = readCachedMajorVersionByNameIdx( SubProjectId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntMajorVersionObj updateMajorVersion( ICFIntMajorVersionObj Obj ) {
		ICFIntMajorVersionObj obj = Obj;
		schema.getCFIntBackingStore().getTableMajorVersion().updateMajorVersion( null,
			Obj.getMajorVersionRec() );
		obj = (ICFIntMajorVersionObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteMajorVersion( ICFIntMajorVersionObj Obj ) {
		ICFIntMajorVersionObj obj = Obj;
		schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersion( null,
			obj.getMajorVersionRec() );
		Obj.forget();
	}

	@Override
	public void deleteMajorVersionByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntMajorVersionObj obj = readMajorVersion(Id);
		if( obj != null ) {
			ICFIntMajorVersionEditObj editObj = (ICFIntMajorVersionEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntMajorVersionEditObj)obj.beginEdit();
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
		deepDisposeMajorVersionByIdIdx( Id );
	}

	@Override
	public void deleteMajorVersionByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntMajorVersionByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntMajorVersionByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMajorVersionObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionByTenantIdx( null,
				TenantId );
			Iterator<ICFIntMajorVersionObj> iter = dict.values().iterator();
			ICFIntMajorVersionObj obj;
			List<ICFIntMajorVersionObj> toForget = new LinkedList<ICFIntMajorVersionObj>();
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
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionByTenantIdx( null,
				TenantId );
		}
		deepDisposeMajorVersionByTenantIdx( TenantId );
	}

	@Override
	public void deleteMajorVersionBySubProjectIdx( CFLibDbKeyHash256 SubProjectId )
	{
		ICFIntMajorVersionBySubProjectIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newBySubProjectIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		if( indexBySubProjectIdx == null ) {
			indexBySubProjectIdx = new HashMap< ICFIntMajorVersionBySubProjectIdxKey,
				Map< CFLibDbKeyHash256, ICFIntMajorVersionObj > >();
		}
		if( indexBySubProjectIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntMajorVersionObj> dict = indexBySubProjectIdx.get( key );
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionBySubProjectIdx( null,
				SubProjectId );
			Iterator<ICFIntMajorVersionObj> iter = dict.values().iterator();
			ICFIntMajorVersionObj obj;
			List<ICFIntMajorVersionObj> toForget = new LinkedList<ICFIntMajorVersionObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexBySubProjectIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionBySubProjectIdx( null,
				SubProjectId );
		}
		deepDisposeMajorVersionBySubProjectIdx( SubProjectId );
	}

	@Override
	public void deleteMajorVersionByNameIdx( CFLibDbKeyHash256 SubProjectId,
		String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntMajorVersionByNameIdxKey,
				ICFIntMajorVersionObj >();
		}
		ICFIntMajorVersionByNameIdxKey key = schema.getCFIntBackingStore().getFactoryMajorVersion().newByNameIdxKey();
		key.setRequiredSubProjectId( SubProjectId );
		key.setRequiredName( Name );
		ICFIntMajorVersionObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionByNameIdx( null,
				SubProjectId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableMajorVersion().deleteMajorVersionByNameIdx( null,
				SubProjectId,
				Name );
		}
		deepDisposeMajorVersionByNameIdx( SubProjectId,
				Name );
	}
}