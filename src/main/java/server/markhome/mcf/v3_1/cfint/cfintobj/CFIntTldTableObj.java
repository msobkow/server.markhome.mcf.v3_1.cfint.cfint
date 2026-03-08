// Description: Java 25 Table Object implementation for Tld.

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

public class CFIntTldTableObj
	implements ICFIntTldTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntTld.CLASS_CODE;
	protected static final int backingClassCode = ICFIntTld.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntTldObj> members;
	private Map<CFLibDbKeyHash256, ICFIntTldObj> allTld;
	private Map< ICFIntTldByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntTldObj > > indexByTenantIdx;
	private Map< ICFIntTldByNameIdxKey,
		ICFIntTldObj > indexByNameIdx;
	public static String TABLE_NAME = "Tld";
	public static String TABLE_DBNAME = "tlddef";

	public CFIntTldTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTldObj>();
		allTld = null;
		indexByTenantIdx = null;
		indexByNameIdx = null;
	}

	public CFIntTldTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTldObj>();
		allTld = null;
		indexByTenantIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntTldTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntTldTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		allTld = null;
		indexByTenantIdx = null;
		indexByNameIdx = null;
		List<ICFIntTldObj> toForget = new LinkedList<ICFIntTldObj>();
		ICFIntTldObj cur = null;
		Iterator<ICFIntTldObj> iter = members.values().iterator();
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
	 *	CFIntTldObj.
	 */
	@Override
	public ICFIntTldObj newInstance() {
		ICFIntTldObj inst = new CFIntTldObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTldObj.
	 */
	@Override
	public ICFIntTldEditObj newEditInstance( ICFIntTldObj orig ) {
		ICFIntTldEditObj edit = new CFIntTldEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntTldObj realiseTld( ICFIntTldObj Obj ) {
		ICFIntTldObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTldObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntTldObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntTldByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTldObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTldByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntTldByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTldObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTldByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allTld != null ) {
				allTld.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTld != null ) {
				allTld.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntTldByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTldObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTldByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntTldObj createTld( ICFIntTldObj Obj ) {
		ICFIntTldObj obj = Obj;
		ICFIntTld rec = obj.getTldRec();
		schema.getCFIntBackingStore().getTableTld().createTld(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntTldObj readTld( CFLibDbKeyHash256 pkey ) {
		return( readTld( pkey, false ) );
	}

	@Override
	public ICFIntTldObj readTld( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntTldObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntTld readRec = schema.getCFIntBackingStore().getTableTld().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTldTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntTldObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTldObj readCachedTld( CFLibDbKeyHash256 pkey ) {
		ICFIntTldObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTld( ICFIntTldObj obj )
	{
		final String S_ProcName = "CFIntTldTableObj.reallyDeepDisposeTld() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTldObj existing = readCachedTld( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntTldByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntTldByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
		keyNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getTopDomainTableObj().deepDisposeTopDomainByTldIdx( existing.getRequiredId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeTld( CFLibDbKeyHash256 pkey ) {
		ICFIntTldObj obj = readCachedTld( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTldObj lockTld( CFLibDbKeyHash256 pkey ) {
		ICFIntTldObj locked = null;
		ICFIntTld lockRec = schema.getCFIntBackingStore().getTableTld().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTldTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntTldObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTld", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntTldObj> readAllTld() {
		return( readAllTld( false ) );
	}

	@Override
	public List<ICFIntTldObj> readAllTld( boolean forceRead ) {
		final String S_ProcName = "readAllTld";
		if( ( allTld == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntTldObj> map = new HashMap<CFLibDbKeyHash256,ICFIntTldObj>();
			allTld = map;
			ICFIntTld[] recList = schema.getCFIntBackingStore().getTableTld().readAllDerived( null );
			ICFIntTld rec;
			ICFIntTldObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTldObj realised = (ICFIntTldObj)obj.realise();
			}
		}
		int len = allTld.size();
		ICFIntTldObj arr[] = new ICFIntTldObj[len];
		Iterator<ICFIntTldObj> valIter = allTld.values().iterator();
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
		ArrayList<ICFIntTldObj> arrayList = new ArrayList<ICFIntTldObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTldObj> cmp = new Comparator<ICFIntTldObj>() {
			@Override
			public int compare( ICFIntTldObj lhs, ICFIntTldObj rhs ) {
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
		List<ICFIntTldObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntTldObj> readCachedAllTld() {
		final String S_ProcName = "readCachedAllTld";
		ArrayList<ICFIntTldObj> arrayList = new ArrayList<ICFIntTldObj>();
		if( allTld != null ) {
			int len = allTld.size();
			ICFIntTldObj arr[] = new ICFIntTldObj[len];
			Iterator<ICFIntTldObj> valIter = allTld.values().iterator();
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
		Comparator<ICFIntTldObj> cmp = new Comparator<ICFIntTldObj>() {
			public int compare( ICFIntTldObj lhs, ICFIntTldObj rhs ) {
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
	public ICFIntTldObj readTldByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readTldByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntTldObj readTldByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntTldObj obj = readTld( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntTldObj> readTldByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTldByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntTldObj> readTldByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTldByTenantIdx";
		ICFIntTldByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntTldObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTldByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTldObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntTldObj>();
			ICFIntTldObj obj;
			ICFIntTld[] recList = schema.getCFIntBackingStore().getTableTld().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntTld rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTldTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTldObj realised = (ICFIntTldObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntTldObj arr[] = new ICFIntTldObj[len];
		Iterator<ICFIntTldObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntTldObj> arrayList = new ArrayList<ICFIntTldObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTldObj> cmp = new Comparator<ICFIntTldObj>() {
			@Override
			public int compare( ICFIntTldObj lhs, ICFIntTldObj rhs ) {
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
		List<ICFIntTldObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntTldObj readTldByNameIdx( String Name )
	{
		return( readTldByNameIdx( Name,
			false ) );
	}

	@Override
	public ICFIntTldObj readTldByNameIdx( String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTldByNameIdxKey,
				ICFIntTldObj >();
		}
		ICFIntTldByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
		key.setRequiredName( Name );
		ICFIntTldObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntTld rec = schema.getCFIntBackingStore().getTableTld().readDerivedByNameIdx( null,
				Name );
			if( rec != null ) {
				obj = schema.getTldTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntTldObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTldObj readCachedTldByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTldObj obj = null;
		obj = readCachedTld( Id );
		return( obj );
	}

	@Override
	public List<ICFIntTldObj> readCachedTldByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTldByTenantIdx";
		ICFIntTldByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntTldObj> arrayList = new ArrayList<ICFIntTldObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntTldObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntTldObj arr[] = new ICFIntTldObj[len];
				Iterator<ICFIntTldObj> valIter = dict.values().iterator();
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
			ICFIntTldObj obj;
			Iterator<ICFIntTldObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntTldObj> cmp = new Comparator<ICFIntTldObj>() {
			@Override
			public int compare( ICFIntTldObj lhs, ICFIntTldObj rhs ) {
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
	public ICFIntTldObj readCachedTldByNameIdx( String Name )
	{
		ICFIntTldObj obj = null;
		ICFIntTldByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntTldObj> valIter = members.values().iterator();
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
			Iterator<ICFIntTldObj> valIter = members.values().iterator();
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
	public void deepDisposeTldByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTldObj obj = readCachedTldByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTldByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTldByTenantIdx";
		ICFIntTldObj obj;
		List<ICFIntTldObj> arrayList = readCachedTldByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntTldObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTldByNameIdx( String Name )
	{
		ICFIntTldObj obj = readCachedTldByNameIdx( Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTldObj updateTld( ICFIntTldObj Obj ) {
		ICFIntTldObj obj = Obj;
		schema.getCFIntBackingStore().getTableTld().updateTld( null,
			Obj.getTldRec() );
		obj = (ICFIntTldObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTld( ICFIntTldObj Obj ) {
		ICFIntTldObj obj = Obj;
		schema.getCFIntBackingStore().getTableTld().deleteTld( null,
			obj.getTldRec() );
		Obj.forget();
	}

	@Override
	public void deleteTldByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTldObj obj = readTld(Id);
		if( obj != null ) {
			ICFIntTldEditObj editObj = (ICFIntTldEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntTldEditObj)obj.beginEdit();
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
		deepDisposeTldByIdIdx( Id );
	}

	@Override
	public void deleteTldByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntTldByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTldByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTldObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntTldObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableTld().deleteTldByTenantIdx( null,
				TenantId );
			Iterator<ICFIntTldObj> iter = dict.values().iterator();
			ICFIntTldObj obj;
			List<ICFIntTldObj> toForget = new LinkedList<ICFIntTldObj>();
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
			schema.getCFIntBackingStore().getTableTld().deleteTldByTenantIdx( null,
				TenantId );
		}
		deepDisposeTldByTenantIdx( TenantId );
	}

	@Override
	public void deleteTldByNameIdx( String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTldByNameIdxKey,
				ICFIntTldObj >();
		}
		ICFIntTldByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTld().newByNameIdxKey();
		key.setRequiredName( Name );
		ICFIntTldObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableTld().deleteTldByNameIdx( null,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableTld().deleteTldByNameIdx( null,
				Name );
		}
		deepDisposeTldByNameIdx( Name );
	}
}