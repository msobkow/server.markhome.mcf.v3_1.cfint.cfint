// Description: Java 25 Table Object implementation for SecDevice.

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

public class CFIntSecDeviceTableObj
	implements ICFIntSecDeviceTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> members;
	private Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> allSecDevice;
	private Map< ICFSecSecDeviceByNameIdxKey,
		ICFSecSecDeviceObj > indexByNameIdx;
	private Map< ICFSecSecDeviceByUserIdxKey,
		Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj > > indexByUserIdx;
	public static String TABLE_NAME = "SecDevice";
	public static String TABLE_DBNAME = "secdev";

	public CFIntSecDeviceTableObj() {
		schema = null;
		members = new HashMap<ICFSecSecDevicePKey, ICFSecSecDeviceObj>();
		allSecDevice = null;
		indexByNameIdx = null;
		indexByUserIdx = null;
	}

	public CFIntSecDeviceTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<ICFSecSecDevicePKey, ICFSecSecDeviceObj>();
		allSecDevice = null;
		indexByNameIdx = null;
		indexByUserIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecSecDeviceTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecSecDeviceTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecSecDeviceTableObj.getRuntimeClassCode() );
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
		allSecDevice = null;
		indexByNameIdx = null;
		indexByUserIdx = null;
		List<ICFSecSecDeviceObj> toForget = new LinkedList<ICFSecSecDeviceObj>();
		ICFSecSecDeviceObj cur = null;
		Iterator<ICFSecSecDeviceObj> iter = members.values().iterator();
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
	 *	CFIntSecDeviceObj.
	 */
	@Override
	public ICFSecSecDeviceObj newInstance() {
		ICFSecSecDeviceObj inst = new CFIntSecDeviceObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntSecDeviceObj.
	 */
	@Override
	public ICFSecSecDeviceEditObj newEditInstance( ICFSecSecDeviceObj orig ) {
		ICFSecSecDeviceEditObj edit = new CFIntSecDeviceEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecSecDeviceObj realiseSecDevice( ICFSecSecDeviceObj Obj ) {
		ICFSecSecDeviceObj obj = Obj;
		ICFSecSecDevicePKey pkey = obj.getPKey();
		ICFSecSecDeviceObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecSecDeviceObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByNameIdx != null ) {
				ICFSecSecDeviceByNameIdxKey keyNameIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
				keyNameIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyNameIdx.setRequiredDevName( keepObj.getRequiredDevName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			if( indexByUserIdx != null ) {
				ICFSecSecDeviceByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.remove( keepObj.getPKey() );
					if( mapUserIdx.size() <= 0 ) {
						indexByUserIdx.remove( keyUserIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByNameIdx != null ) {
				ICFSecSecDeviceByNameIdxKey keyNameIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
				keyNameIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyNameIdx.setRequiredDevName( keepObj.getRequiredDevName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( indexByUserIdx != null ) {
				ICFSecSecDeviceByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allSecDevice != null ) {
				allSecDevice.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allSecDevice != null ) {
				allSecDevice.put( keepObj.getPKey(), keepObj );
			}

			if( indexByNameIdx != null ) {
				ICFSecSecDeviceByNameIdxKey keyNameIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
				keyNameIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				keyNameIdx.setRequiredDevName( keepObj.getRequiredDevName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( indexByUserIdx != null ) {
				ICFSecSecDeviceByUserIdxKey keyUserIdx =
					schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
				keyUserIdx.setRequiredSecUserId( keepObj.getRequiredSecUserId() );
				Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj > mapUserIdx = indexByUserIdx.get( keyUserIdx );
				if( mapUserIdx != null ) {
					mapUserIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecSecDeviceObj createSecDevice( ICFSecSecDeviceObj Obj ) {
		ICFSecSecDeviceObj obj = Obj;
		ICFSecSecDevice rec = obj.getSecDeviceRec();
		schema.getCFSecBackingStore().getTableSecDevice().createSecDevice(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecSecDeviceObj readSecDevice( ICFSecSecDevicePKey pkey ) {
		return( readSecDevice( pkey, false ) );
	}

	@Override
	public ICFSecSecDeviceObj readSecDevice( ICFSecSecDevicePKey pkey, boolean forceRead ) {
		ICFSecSecDeviceObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecSecDevice readRec = schema.getCFSecBackingStore().getTableSecDevice().readDerivedByIdIdx( null,
						pkey.getRequiredSecUserId(),
						pkey.getRequiredDevName() );
			if( readRec != null ) {
				obj = schema.getSecDeviceTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecSecDeviceObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecSecDeviceObj readSecDevice( CFLibDbKeyHash256 SecUserId,
		String DevName ) {
		return( readSecDevice( SecUserId,
			DevName, false ) );
	}

	@Override
	public ICFSecSecDeviceObj readSecDevice( CFLibDbKeyHash256 SecUserId,
		String DevName, boolean forceRead ) {
		ICFSecSecDeviceObj obj = null;
		ICFSecSecDevice readRec = schema.getCFSecBackingStore().getTableSecDevice().readDerivedByIdIdx( null,
			SecUserId,
			DevName );
		if( readRec != null ) {
				obj = schema.getSecDeviceTableObj().newInstance();
			obj.setPKey( readRec.getPKey() );
			obj.setRec( readRec );
			obj = (ICFSecSecDeviceObj)obj.realise();
		}
		return( obj );
	}

	@Override
	public ICFSecSecDeviceObj readCachedSecDevice( ICFSecSecDevicePKey pkey ) {
		ICFSecSecDeviceObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeSecDevice( ICFSecSecDeviceObj obj )
	{
		final String S_ProcName = "CFIntSecDeviceTableObj.reallyDeepDisposeSecDevice() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		ICFSecSecDevicePKey pkey = obj.getPKey();
		ICFSecSecDeviceObj existing = readCachedSecDevice( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecSecDeviceByNameIdxKey keyNameIdx = schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
		keyNameIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );
		keyNameIdx.setRequiredDevName( existing.getRequiredDevName() );

		ICFSecSecDeviceByUserIdxKey keyUserIdx = schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
		keyUserIdx.setRequiredSecUserId( existing.getRequiredSecUserId() );



		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}

		if( indexByUserIdx != null ) {
			if( indexByUserIdx.containsKey( keyUserIdx ) ) {
				indexByUserIdx.get( keyUserIdx ).remove( pkey );
				if( indexByUserIdx.get( keyUserIdx ).size() <= 0 ) {
					indexByUserIdx.remove( keyUserIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeSecDevice( ICFSecSecDevicePKey pkey ) {
		ICFSecSecDeviceObj obj = readCachedSecDevice( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecSecDeviceObj lockSecDevice( ICFSecSecDevicePKey pkey ) {
		ICFSecSecDeviceObj locked = null;
		ICFSecSecDevice lockRec = schema.getCFSecBackingStore().getTableSecDevice().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getSecDeviceTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecSecDeviceObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockSecDevice", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecSecDeviceObj> readAllSecDevice() {
		return( readAllSecDevice( false ) );
	}

	@Override
	public List<ICFSecSecDeviceObj> readAllSecDevice( boolean forceRead ) {
		final String S_ProcName = "readAllSecDevice";
		if( ( allSecDevice == null ) || forceRead ) {
			Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> map = new HashMap<ICFSecSecDevicePKey,ICFSecSecDeviceObj>();
			allSecDevice = map;
			ICFSecSecDevice[] recList = schema.getCFSecBackingStore().getTableSecDevice().readAllDerived( null );
			ICFSecSecDevice rec;
			ICFSecSecDeviceObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecDeviceObj realised = (ICFSecSecDeviceObj)obj.realise();
			}
		}
		int len = allSecDevice.size();
		ICFSecSecDeviceObj arr[] = new ICFSecSecDeviceObj[len];
		Iterator<ICFSecSecDeviceObj> valIter = allSecDevice.values().iterator();
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
		ArrayList<ICFSecSecDeviceObj> arrayList = new ArrayList<ICFSecSecDeviceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecDeviceObj> cmp = new Comparator<ICFSecSecDeviceObj>() {
			@Override
			public int compare( ICFSecSecDeviceObj lhs, ICFSecSecDeviceObj rhs ) {
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
					ICFSecSecDevicePKey lhsPKey = lhs.getPKey();
					ICFSecSecDevicePKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecDeviceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecSecDeviceObj> readCachedAllSecDevice() {
		final String S_ProcName = "readCachedAllSecDevice";
		ArrayList<ICFSecSecDeviceObj> arrayList = new ArrayList<ICFSecSecDeviceObj>();
		if( allSecDevice != null ) {
			int len = allSecDevice.size();
			ICFSecSecDeviceObj arr[] = new ICFSecSecDeviceObj[len];
			Iterator<ICFSecSecDeviceObj> valIter = allSecDevice.values().iterator();
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
		Comparator<ICFSecSecDeviceObj> cmp = new Comparator<ICFSecSecDeviceObj>() {
			public int compare( ICFSecSecDeviceObj lhs, ICFSecSecDeviceObj rhs ) {
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
					ICFSecSecDevicePKey lhsPKey = lhs.getPKey();
					ICFSecSecDevicePKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	/**
	 *	Return a sorted map of a page of the SecDevice-derived instances in the database.
	 *
	 *	@return	List of ICFSecSecDeviceObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecSecDeviceObj> pageAllSecDevice(CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
	{
		final String S_ProcName = "pageAllSecDevice";
		Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> map = new HashMap<ICFSecSecDevicePKey,ICFSecSecDeviceObj>();
		ICFSecSecDevice[] recList = schema.getCFSecBackingStore().getTableSecDevice().pageAllRec( null,
			priorSecUserId,
			priorDevName );
		ICFSecSecDevice rec;
		ICFSecSecDeviceObj obj;
		ICFSecSecDeviceObj realised;
		ArrayList<ICFSecSecDeviceObj> arrayList = new ArrayList<ICFSecSecDeviceObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecSecDeviceObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecSecDeviceObj readSecDeviceByIdIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		return( readSecDeviceByIdIdx( SecUserId,
			DevName,
			false ) );
	}

	@Override
	public ICFSecSecDeviceObj readSecDeviceByIdIdx( CFLibDbKeyHash256 SecUserId,
		String DevName, boolean forceRead )
	{
		ICFSecSecDevicePKey pkey = schema.getCFSecBackingStore().getFactorySecDevice().newPKey();
		pkey.setRequiredContainerSecUser(SecUserId);
		pkey.setRequiredDevName( DevName );
		ICFSecSecDeviceObj obj = readSecDevice( pkey, forceRead );
		return( obj );
	}

	@Override
	public ICFSecSecDeviceObj readSecDeviceByNameIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		return( readSecDeviceByNameIdx( SecUserId,
			DevName,
			false ) );
	}

	@Override
	public ICFSecSecDeviceObj readSecDeviceByNameIdx( CFLibDbKeyHash256 SecUserId,
		String DevName, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFSecSecDeviceByNameIdxKey,
				ICFSecSecDeviceObj >();
		}
		ICFSecSecDeviceByNameIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredDevName( DevName );
		ICFSecSecDeviceObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFSecSecDevice rec = schema.getCFSecBackingStore().getTableSecDevice().readDerivedByNameIdx( null,
				SecUserId,
				DevName );
			if( rec != null ) {
				obj = schema.getSecDeviceTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecSecDeviceObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public List<ICFSecSecDeviceObj> readSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		return( readSecDeviceByUserIdx( SecUserId,
			false ) );
	}

	@Override
	public List<ICFSecSecDeviceObj> readSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId,
		boolean forceRead )
	{
		final String S_ProcName = "readSecDeviceByUserIdx";
		ICFSecSecDeviceByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> dict;
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecSecDeviceByUserIdxKey,
				Map< ICFSecSecDevicePKey, ICFSecSecDeviceObj > >();
		}
		if( ( ! forceRead ) && indexByUserIdx.containsKey( key ) ) {
			dict = indexByUserIdx.get( key );
		}
		else {
			dict = new HashMap<ICFSecSecDevicePKey, ICFSecSecDeviceObj>();
			ICFSecSecDeviceObj obj;
			ICFSecSecDevice[] recList = schema.getCFSecBackingStore().getTableSecDevice().readDerivedByUserIdx( null,
				SecUserId );
			ICFSecSecDevice rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getSecDeviceTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecSecDeviceObj realised = (ICFSecSecDeviceObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByUserIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecSecDeviceObj arr[] = new ICFSecSecDeviceObj[len];
		Iterator<ICFSecSecDeviceObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecSecDeviceObj> arrayList = new ArrayList<ICFSecSecDeviceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecSecDeviceObj> cmp = new Comparator<ICFSecSecDeviceObj>() {
			@Override
			public int compare( ICFSecSecDeviceObj lhs, ICFSecSecDeviceObj rhs ) {
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
					ICFSecSecDevicePKey lhsPKey = lhs.getPKey();
					ICFSecSecDevicePKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecSecDeviceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecSecDeviceObj readCachedSecDeviceByIdIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		ICFSecSecDeviceObj obj = null;
		ICFSecSecDevicePKey pkey = schema.getCFSecBackingStore().getFactorySecDevice().newPKey();
		pkey.setRequiredContainerSecUser(SecUserId);
		pkey.setRequiredDevName( DevName );
		pkey.setRequiredContainerSecUser(SecUserId);
		pkey.setRequiredDevName( DevName );
		obj = readCachedSecDevice( pkey );
		return( obj );
	}

	@Override
	public ICFSecSecDeviceObj readCachedSecDeviceByNameIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		ICFSecSecDeviceObj obj = null;
		ICFSecSecDeviceByNameIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredDevName( DevName );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFSecSecDeviceObj> valIter = members.values().iterator();
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
			Iterator<ICFSecSecDeviceObj> valIter = members.values().iterator();
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
	public List<ICFSecSecDeviceObj> readCachedSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "readCachedSecDeviceByUserIdx";
		ICFSecSecDeviceByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		ArrayList<ICFSecSecDeviceObj> arrayList = new ArrayList<ICFSecSecDeviceObj>();
		if( indexByUserIdx != null ) {
			Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> dict;
			if( indexByUserIdx.containsKey( key ) ) {
				dict = indexByUserIdx.get( key );
				int len = dict.size();
				ICFSecSecDeviceObj arr[] = new ICFSecSecDeviceObj[len];
				Iterator<ICFSecSecDeviceObj> valIter = dict.values().iterator();
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
			ICFSecSecDeviceObj obj;
			Iterator<ICFSecSecDeviceObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecSecDeviceObj> cmp = new Comparator<ICFSecSecDeviceObj>() {
			@Override
			public int compare( ICFSecSecDeviceObj lhs, ICFSecSecDeviceObj rhs ) {
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
					ICFSecSecDevicePKey lhsPKey = lhs.getPKey();
					ICFSecSecDevicePKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public void deepDisposeSecDeviceByIdIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		ICFSecSecDeviceObj obj = readCachedSecDeviceByIdIdx( SecUserId,
				DevName );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecDeviceByNameIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		ICFSecSecDeviceObj obj = readCachedSecDeviceByNameIdx( SecUserId,
				DevName );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		final String S_ProcName = "deepDisposeSecDeviceByUserIdx";
		ICFSecSecDeviceObj obj;
		List<ICFSecSecDeviceObj> arrayList = readCachedSecDeviceByUserIdx( SecUserId );
		if( arrayList != null )  {
			Iterator<ICFSecSecDeviceObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	/**
	 *	Read a page of data as a List of SecDevice-derived instances sorted by their primary keys,
	 *	as identified by the duplicate UserIdx key attributes.
	 *
	 *	@param	SecUserId	The SecDevice key attribute of the instance generating the id.
	 *
	 *	@return	A List of SecDevice-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecSecDeviceObj> pageSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId,
		CFLibDbKeyHash256 priorSecUserId,
		String priorDevName )
	{
		final String S_ProcName = "pageSecDeviceByUserIdx";
		ICFSecSecDeviceByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		List<ICFSecSecDeviceObj> retList = new LinkedList<ICFSecSecDeviceObj>();
		ICFSecSecDeviceObj obj;
		ICFSecSecDevice[] recList = schema.getCFSecBackingStore().getTableSecDevice().pageRecByUserIdx( null,
				SecUserId,
			priorSecUserId,
			priorDevName );
		ICFSecSecDevice rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getSecDeviceTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecSecDeviceObj realised = (ICFSecSecDeviceObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecSecDeviceObj updateSecDevice( ICFSecSecDeviceObj Obj ) {
		ICFSecSecDeviceObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecDevice().updateSecDevice( null,
			Obj.getSecDeviceRec() );
		obj = (ICFSecSecDeviceObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteSecDevice( ICFSecSecDeviceObj Obj ) {
		ICFSecSecDeviceObj obj = Obj;
		schema.getCFSecBackingStore().getTableSecDevice().deleteSecDevice( null,
			obj.getSecDeviceRec() );
		Obj.forget();
	}

	@Override
	public void deleteSecDeviceByIdIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		ICFSecSecDeviceObj obj = readSecDevice(SecUserId,
				DevName);
		if( obj != null ) {
			ICFSecSecDeviceEditObj editObj = (ICFSecSecDeviceEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecSecDeviceEditObj)obj.beginEdit();
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
		deepDisposeSecDeviceByIdIdx( SecUserId,
				DevName );
	}

	@Override
	public void deleteSecDeviceByNameIdx( CFLibDbKeyHash256 SecUserId,
		String DevName )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFSecSecDeviceByNameIdxKey,
				ICFSecSecDeviceObj >();
		}
		ICFSecSecDeviceByNameIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByNameIdxKey();
		key.setRequiredSecUserId( SecUserId );
		key.setRequiredDevName( DevName );
		ICFSecSecDeviceObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFSecBackingStore().getTableSecDevice().deleteSecDeviceByNameIdx( null,
				SecUserId,
				DevName );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableSecDevice().deleteSecDeviceByNameIdx( null,
				SecUserId,
				DevName );
		}
		deepDisposeSecDeviceByNameIdx( SecUserId,
				DevName );
	}

	@Override
	public void deleteSecDeviceByUserIdx( CFLibDbKeyHash256 SecUserId )
	{
		ICFSecSecDeviceByUserIdxKey key = schema.getCFSecBackingStore().getFactorySecDevice().newByUserIdxKey();
		key.setRequiredSecUserId( SecUserId );
		if( indexByUserIdx == null ) {
			indexByUserIdx = new HashMap< ICFSecSecDeviceByUserIdxKey,
				Map< ICFSecSecDevicePKey, ICFSecSecDeviceObj > >();
		}
		if( indexByUserIdx.containsKey( key ) ) {
			Map<ICFSecSecDevicePKey, ICFSecSecDeviceObj> dict = indexByUserIdx.get( key );
			schema.getCFSecBackingStore().getTableSecDevice().deleteSecDeviceByUserIdx( null,
				SecUserId );
			Iterator<ICFSecSecDeviceObj> iter = dict.values().iterator();
			ICFSecSecDeviceObj obj;
			List<ICFSecSecDeviceObj> toForget = new LinkedList<ICFSecSecDeviceObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByUserIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableSecDevice().deleteSecDeviceByUserIdx( null,
				SecUserId );
		}
		deepDisposeSecDeviceByUserIdx( SecUserId );
	}
}