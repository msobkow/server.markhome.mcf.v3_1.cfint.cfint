// Description: Java 25 Table Object implementation for ServiceType.

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

public class CFIntServiceTypeTableObj
	implements ICFIntServiceTypeTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecServiceTypeObj> members;
	private Map<CFLibDbKeyHash256, ICFSecServiceTypeObj> allServiceType;
	private Map< ICFSecServiceTypeByUDescrIdxKey,
		ICFSecServiceTypeObj > indexByUDescrIdx;
	public static String TABLE_NAME = "ServiceType";
	public static String TABLE_DBNAME = "svctype";

	public CFIntServiceTypeTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecServiceTypeObj>();
		allServiceType = null;
		indexByUDescrIdx = null;
	}

	public CFIntServiceTypeTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecServiceTypeObj>();
		allServiceType = null;
		indexByUDescrIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecServiceTypeTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecServiceTypeTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecServiceTypeTableObj.getRuntimeClassCode() );
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
		allServiceType = null;
		indexByUDescrIdx = null;
		List<ICFSecServiceTypeObj> toForget = new LinkedList<ICFSecServiceTypeObj>();
		ICFSecServiceTypeObj cur = null;
		Iterator<ICFSecServiceTypeObj> iter = members.values().iterator();
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
	 *	CFIntServiceTypeObj.
	 */
	@Override
	public ICFSecServiceTypeObj newInstance() {
		ICFSecServiceTypeObj inst = new CFIntServiceTypeObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntServiceTypeObj.
	 */
	@Override
	public ICFSecServiceTypeEditObj newEditInstance( ICFSecServiceTypeObj orig ) {
		ICFSecServiceTypeEditObj edit = new CFIntServiceTypeEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecServiceTypeObj realiseServiceType( ICFSecServiceTypeObj Obj ) {
		ICFSecServiceTypeObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecServiceTypeObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecServiceTypeObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByUDescrIdx != null ) {
				ICFSecServiceTypeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.remove( keyUDescrIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByUDescrIdx != null ) {
				ICFSecServiceTypeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

			if( allServiceType != null ) {
				allServiceType.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allServiceType != null ) {
				allServiceType.put( keepObj.getPKey(), keepObj );
			}

			if( indexByUDescrIdx != null ) {
				ICFSecServiceTypeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecServiceTypeObj createServiceType( ICFSecServiceTypeObj Obj ) {
		ICFSecServiceTypeObj obj = Obj;
		ICFSecServiceType rec = obj.getServiceTypeRec();
		schema.getCFSecBackingStore().getTableServiceType().createServiceType(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecServiceTypeObj readServiceType( CFLibDbKeyHash256 pkey ) {
		return( readServiceType( pkey, false ) );
	}

	@Override
	public ICFSecServiceTypeObj readServiceType( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecServiceTypeObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecServiceType readRec = schema.getCFSecBackingStore().getTableServiceType().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getServiceTypeTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecServiceTypeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecServiceTypeObj readCachedServiceType( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceTypeObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeServiceType( ICFSecServiceTypeObj obj )
	{
		final String S_ProcName = "CFIntServiceTypeTableObj.reallyDeepDisposeServiceType() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecServiceTypeObj existing = readCachedServiceType( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecServiceTypeByUDescrIdxKey keyUDescrIdx = schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
		keyUDescrIdx.setRequiredDescription( existing.getRequiredDescription() );


		schema.getServiceTableObj().deepDisposeServiceByTypeIdx( existing.getRequiredServiceTypeId() );

		if( indexByUDescrIdx != null ) {
			indexByUDescrIdx.remove( keyUDescrIdx );
		}


	}
	@Override
	public void deepDisposeServiceType( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceTypeObj obj = readCachedServiceType( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecServiceTypeObj lockServiceType( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceTypeObj locked = null;
		ICFSecServiceType lockRec = schema.getCFSecBackingStore().getTableServiceType().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getServiceTypeTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecServiceTypeObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockServiceType", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecServiceTypeObj> readAllServiceType() {
		return( readAllServiceType( false ) );
	}

	@Override
	public List<ICFSecServiceTypeObj> readAllServiceType( boolean forceRead ) {
		final String S_ProcName = "readAllServiceType";
		if( ( allServiceType == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecServiceTypeObj> map = new HashMap<CFLibDbKeyHash256,ICFSecServiceTypeObj>();
			allServiceType = map;
			ICFSecServiceType[] recList = schema.getCFSecBackingStore().getTableServiceType().readAllDerived( null );
			ICFSecServiceType rec;
			ICFSecServiceTypeObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecServiceTypeObj realised = (ICFSecServiceTypeObj)obj.realise();
			}
		}
		int len = allServiceType.size();
		ICFSecServiceTypeObj arr[] = new ICFSecServiceTypeObj[len];
		Iterator<ICFSecServiceTypeObj> valIter = allServiceType.values().iterator();
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
		ArrayList<ICFSecServiceTypeObj> arrayList = new ArrayList<ICFSecServiceTypeObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecServiceTypeObj> cmp = new Comparator<ICFSecServiceTypeObj>() {
			@Override
			public int compare( ICFSecServiceTypeObj lhs, ICFSecServiceTypeObj rhs ) {
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
		List<ICFSecServiceTypeObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecServiceTypeObj> readCachedAllServiceType() {
		final String S_ProcName = "readCachedAllServiceType";
		ArrayList<ICFSecServiceTypeObj> arrayList = new ArrayList<ICFSecServiceTypeObj>();
		if( allServiceType != null ) {
			int len = allServiceType.size();
			ICFSecServiceTypeObj arr[] = new ICFSecServiceTypeObj[len];
			Iterator<ICFSecServiceTypeObj> valIter = allServiceType.values().iterator();
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
		Comparator<ICFSecServiceTypeObj> cmp = new Comparator<ICFSecServiceTypeObj>() {
			public int compare( ICFSecServiceTypeObj lhs, ICFSecServiceTypeObj rhs ) {
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
	public ICFSecServiceTypeObj readServiceTypeByIdIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		return( readServiceTypeByIdIdx( ServiceTypeId,
			false ) );
	}

	@Override
	public ICFSecServiceTypeObj readServiceTypeByIdIdx( CFLibDbKeyHash256 ServiceTypeId, boolean forceRead )
	{
		ICFSecServiceTypeObj obj = readServiceType( ServiceTypeId, forceRead );
		return( obj );
	}

	@Override
	public ICFSecServiceTypeObj readServiceTypeByUDescrIdx( String Description )
	{
		return( readServiceTypeByUDescrIdx( Description,
			false ) );
	}

	@Override
	public ICFSecServiceTypeObj readServiceTypeByUDescrIdx( String Description, boolean forceRead )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecServiceTypeByUDescrIdxKey,
				ICFSecServiceTypeObj >();
		}
		ICFSecServiceTypeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		ICFSecServiceTypeObj obj = null;
		if( ( ! forceRead ) && indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
		}
		else {
			ICFSecServiceType rec = schema.getCFSecBackingStore().getTableServiceType().readDerivedByUDescrIdx( null,
				Description );
			if( rec != null ) {
				obj = schema.getServiceTypeTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecServiceTypeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecServiceTypeObj readCachedServiceTypeByIdIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceTypeObj obj = null;
		obj = readCachedServiceType( ServiceTypeId );
		return( obj );
	}

	@Override
	public ICFSecServiceTypeObj readCachedServiceTypeByUDescrIdx( String Description )
	{
		ICFSecServiceTypeObj obj = null;
		ICFSecServiceTypeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		if( indexByUDescrIdx != null ) {
			if( indexByUDescrIdx.containsKey( key ) ) {
				obj = indexByUDescrIdx.get( key );
			}
			else {
				Iterator<ICFSecServiceTypeObj> valIter = members.values().iterator();
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
			Iterator<ICFSecServiceTypeObj> valIter = members.values().iterator();
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
	public void deepDisposeServiceTypeByIdIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceTypeObj obj = readCachedServiceTypeByIdIdx( ServiceTypeId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeServiceTypeByUDescrIdx( String Description )
	{
		ICFSecServiceTypeObj obj = readCachedServiceTypeByUDescrIdx( Description );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecServiceTypeObj updateServiceType( ICFSecServiceTypeObj Obj ) {
		ICFSecServiceTypeObj obj = Obj;
		schema.getCFSecBackingStore().getTableServiceType().updateServiceType( null,
			Obj.getServiceTypeRec() );
		obj = (ICFSecServiceTypeObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteServiceType( ICFSecServiceTypeObj Obj ) {
		ICFSecServiceTypeObj obj = Obj;
		schema.getCFSecBackingStore().getTableServiceType().deleteServiceType( null,
			obj.getServiceTypeRec() );
		Obj.forget();
	}

	@Override
	public void deleteServiceTypeByIdIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceTypeObj obj = readServiceType(ServiceTypeId);
		if( obj != null ) {
			ICFSecServiceTypeEditObj editObj = (ICFSecServiceTypeEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecServiceTypeEditObj)obj.beginEdit();
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
		deepDisposeServiceTypeByIdIdx( ServiceTypeId );
	}

	@Override
	public void deleteServiceTypeByUDescrIdx( String Description )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecServiceTypeByUDescrIdxKey,
				ICFSecServiceTypeObj >();
		}
		ICFSecServiceTypeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryServiceType().newByUDescrIdxKey();
		key.setRequiredDescription( Description );
		ICFSecServiceTypeObj obj = null;
		if( indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
			schema.getCFSecBackingStore().getTableServiceType().deleteServiceTypeByUDescrIdx( null,
				Description );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableServiceType().deleteServiceTypeByUDescrIdx( null,
				Description );
		}
		deepDisposeServiceTypeByUDescrIdx( Description );
	}
}