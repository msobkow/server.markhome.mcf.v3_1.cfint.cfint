// Description: Java 25 Table Object implementation for SysCluster.

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
import server.markhome.mcf.v3_1.cfsec.cfsec.*;

public class CFIntSysClusterTableObj
	implements ICFIntSysClusterTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<Integer, ICFSecSysClusterObj> members;
	private Map<Integer, ICFSecSysClusterObj> allSysCluster;
	private Map< ICFSecSysClusterByClusterIdxKey,
		Map<Integer, ICFSecSysClusterObj > > indexByClusterIdx;
	public static String TABLE_NAME = "SysCluster";
	public static String TABLE_DBNAME = "sysclus";

	public CFIntSysClusterTableObj() {
		schema = null;
		members = new HashMap<Integer, ICFSecSysClusterObj>();
		allSysCluster = null;
		indexByClusterIdx = null;
	}

	public CFIntSysClusterTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<Integer, ICFSecSysClusterObj>();
		allSysCluster = null;
		indexByClusterIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSysClusterTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSysClusterTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSysClusterTableObj.getRuntimeClassCode() );
	}

	@Override
	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	@Override
	public void setSchema( ICFSecSchemaObj value ) {
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
		allSysCluster = null;
		indexByClusterIdx = null;
		List<ICFSecSysClusterObj> toForget = new LinkedList<ICFSecSysClusterObj>();
		ICFSecSysClusterObj cur = null;
		Iterator<ICFSecSysClusterObj> iter = members.values().iterator();
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
	 *	CFIntSysClusterObj.
	 */
	@Override
	public ICFSecSysClusterObj newInstance() {
		ICFSecSysClusterObj inst = new CFIntSysClusterObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSysClusterObj.
	 */
	@Override
	public ICFSecSysClusterEditObj newEditInstance( ICFSecSysClusterObj orig ) {
		ICFSecSysClusterEditObj edit = new CFIntSysClusterEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSysClusterObj realiseSysCluster( ICFSecSysClusterObj Obj ) {
		ICFSecSysClusterObj obj = Obj;
		Integer pkey = obj.getPKey();
		ICFSecSysClusterObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSysClusterObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecSysClusterByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<Integer, ICFSecSysClusterObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecSysClusterByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<Integer, ICFSecSysClusterObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allSysCluster != null ) {
				allSysCluster.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSysCluster != null ) {
				allSysCluster.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecSysClusterByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<Integer, ICFSecSysClusterObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSysClusterObj createSysCluster( ICFSecSysClusterObj Obj ) {
		ICFSecSysClusterObj obj = Obj;
		ICFSecSysCluster rec = obj.getSysClusterRec();
		schema.getCFSecBackingStore().getTableSysCluster().createSysCluster(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSysClusterObj readSysCluster( Integer pkey ) {
		return( readSysCluster( pkey, false ) );
	}

	@Override
	public ICFSecSysClusterObj readSysCluster( Integer pkey, boolean forceRead ) {
		ICFSecSysClusterObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSysCluster readRec = schema.getCFSecBackingStore().getTableSysCluster().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getSysClusterTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSysClusterObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSysClusterObj readCachedSysCluster( Integer pkey ) {
		ICFSecSysClusterObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSysCluster( ICFSecSysClusterObj obj )
	{
		final String S_ProcName = "CFIntSysClusterTableObj.reallyDeepDisposeSysCluster() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		Integer pkey = obj.getPKey();
		ICFSecSysClusterObj existing = readCachedSysCluster( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSysClusterByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );



		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeSysCluster( Integer pkey ) {
		ICFSecSysClusterObj obj = readCachedSysCluster( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSysClusterObj lockSysCluster( Integer pkey ) {
		ICFSecSysClusterObj locked = null;
		ICFSecSysCluster lockRec = schema.getCFSecBackingStore().getTableSysCluster().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSysClusterTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSysClusterObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSysCluster", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSysClusterObj> readAllSysCluster() {
		return( readAllSysCluster( false ) );
	}

	@Override
	public List<ICFSecSysClusterObj> readAllSysCluster( boolean forceRead ) {
		final String S_ProcName = "readAllSysCluster";
		if( ( allSysCluster == null ) || forceRead ) {
			Map<Integer, ICFSecSysClusterObj> map = new HashMap<Integer,ICFSecSysClusterObj>();
			allSysCluster = map;
			ICFSecSysCluster[] recList = schema.getCFSecBackingStore().getTableSysCluster().readAllDerived( null );
			ICFSecSysCluster rec;
			ICFSecSysClusterObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSysClusterObj realised = (ICFSecSysClusterObj)obj.realise();
			}
		}
		int len = allSysCluster.size();
		ICFSecSysClusterObj arr[] = new ICFSecSysClusterObj[len];
		Iterator<ICFSecSysClusterObj> valIter = allSysCluster.values().iterator();
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
		ArrayList<ICFSecSysClusterObj> arrayList = new ArrayList<ICFSecSysClusterObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSysClusterObj> cmp = new Comparator<ICFSecSysClusterObj>() {
			@Override
			public int compare( ICFSecSysClusterObj lhs, ICFSecSysClusterObj rhs ) {
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
		List<ICFSecSysClusterObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSysClusterObj> readCachedAllSysCluster() {
		final String S_ProcName = "readCachedAllSysCluster";
		ArrayList<ICFSecSysClusterObj> arrayList = new ArrayList<ICFSecSysClusterObj>();
		if( allSysCluster != null ) {
			int len = allSysCluster.size();
			ICFSecSysClusterObj arr[] = new ICFSecSysClusterObj[len];
			Iterator<ICFSecSysClusterObj> valIter = allSysCluster.values().iterator();
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
		Comparator<ICFSecSysClusterObj> cmp = new Comparator<ICFSecSysClusterObj>() {
			public int compare( ICFSecSysClusterObj lhs, ICFSecSysClusterObj rhs ) {
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
	public ICFSecSysClusterObj readSysClusterByIdIdx( int SingletonId )
	{
		return( readSysClusterByIdIdx( SingletonId,
			false ) );
	}

	@Override
	public ICFSecSysClusterObj readSysClusterByIdIdx( int SingletonId, boolean forceRead )
	{
		ICFSecSysClusterObj obj = readSysCluster( SingletonId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecSysClusterObj> readSysClusterByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readSysClusterByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecSysClusterObj> readSysClusterByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readSysClusterByClusterIdx";
		ICFSecSysClusterByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<Integer, ICFSecSysClusterObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSysClusterByClusterIdxKey,
				Map< Integer, ICFSecSysClusterObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<Integer, ICFSecSysClusterObj>();
			ICFSecSysClusterObj obj;
			ICFSecSysCluster[] recList = schema.getCFSecBackingStore().getTableSysCluster().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecSysCluster rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSysClusterTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSysClusterObj realised = (ICFSecSysClusterObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSysClusterObj arr[] = new ICFSecSysClusterObj[len];
		Iterator<ICFSecSysClusterObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSysClusterObj> arrayList = new ArrayList<ICFSecSysClusterObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSysClusterObj> cmp = new Comparator<ICFSecSysClusterObj>() {
			@Override
			public int compare( ICFSecSysClusterObj lhs, ICFSecSysClusterObj rhs ) {
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
		List<ICFSecSysClusterObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSysClusterObj readCachedSysClusterByIdIdx( int SingletonId )
	{
		ICFSecSysClusterObj obj = null;
		obj = readCachedSysCluster( SingletonId );
		return( obj );
	}

	@Override
	public List<ICFSecSysClusterObj> readCachedSysClusterByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedSysClusterByClusterIdx";
		ICFSecSysClusterByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecSysClusterObj> arrayList = new ArrayList<ICFSecSysClusterObj>();
		if( indexByClusterIdx != null ) {
			Map<Integer, ICFSecSysClusterObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecSysClusterObj arr[] = new ICFSecSysClusterObj[len];
				Iterator<ICFSecSysClusterObj> valIter = dict.values().iterator();
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
			ICFSecSysClusterObj obj;
			Iterator<ICFSecSysClusterObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSysClusterObj> cmp = new Comparator<ICFSecSysClusterObj>() {
			@Override
			public int compare( ICFSecSysClusterObj lhs, ICFSecSysClusterObj rhs ) {
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
	public void deepDisposeSysClusterByIdIdx( int SingletonId )
	{
		ICFSecSysClusterObj obj = readCachedSysClusterByIdIdx( SingletonId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSysClusterByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeSysClusterByClusterIdx";
		ICFSecSysClusterObj obj;
		List<ICFSecSysClusterObj> arrayList = readCachedSysClusterByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecSysClusterObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public ICFSecSysClusterObj updateSysCluster( ICFSecSysClusterObj Obj ) {
		ICFSecSysClusterObj obj = Obj;
		schema.getCFSecBackingStore().getTableSysCluster().updateSysCluster( null,
			Obj.getSysClusterRec() );
		obj = (ICFSecSysClusterObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSysCluster( ICFSecSysClusterObj Obj ) {
		ICFSecSysClusterObj obj = Obj;
		schema.getCFSecBackingStore().getTableSysCluster().deleteSysCluster( null,
			obj.getSysClusterRec() );
		Obj.forget();
	}

	@Override
	public void deleteSysClusterByIdIdx( int SingletonId )
	{
		ICFSecSysClusterObj obj = readSysCluster(SingletonId);
		if( obj != null ) {
			ICFSecSysClusterEditObj editObj = (ICFSecSysClusterEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSysClusterEditObj)obj.beginEdit();
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
		deepDisposeSysClusterByIdIdx( SingletonId );
	}

	@Override
	public void deleteSysClusterByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecSysClusterByClusterIdxKey key = schema.getCFSecBackingStore().getFactorySysCluster().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecSysClusterByClusterIdxKey,
				Map< Integer, ICFSecSysClusterObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<Integer, ICFSecSysClusterObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableSysCluster().deleteSysClusterByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecSysClusterObj> iter = dict.values().iterator();
			ICFSecSysClusterObj obj;
			List<ICFSecSysClusterObj> toForget = new LinkedList<ICFSecSysClusterObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByClusterIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSysCluster().deleteSysClusterByClusterIdx( null,
				ClusterId );
		}
		deepDisposeSysClusterByClusterIdx( ClusterId );
	}
}