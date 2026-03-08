// Description: Java 25 Table Object implementation for Service.

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

public class CFIntServiceTableObj
	implements ICFIntServiceTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecServiceObj> members;
	private Map<CFLibDbKeyHash256, ICFSecServiceObj> allService;
	private Map< ICFSecServiceByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecServiceObj > > indexByClusterIdx;
	private Map< ICFSecServiceByHostIdxKey,
		Map<CFLibDbKeyHash256, ICFSecServiceObj > > indexByHostIdx;
	private Map< ICFSecServiceByTypeIdxKey,
		Map<CFLibDbKeyHash256, ICFSecServiceObj > > indexByTypeIdx;
	private Map< ICFSecServiceByUTypeIdxKey,
		ICFSecServiceObj > indexByUTypeIdx;
	private Map< ICFSecServiceByUHostPortIdxKey,
		ICFSecServiceObj > indexByUHostPortIdx;
	public static String TABLE_NAME = "Service";
	public static String TABLE_DBNAME = "hostsvc";

	public CFIntServiceTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecServiceObj>();
		allService = null;
		indexByClusterIdx = null;
		indexByHostIdx = null;
		indexByTypeIdx = null;
		indexByUTypeIdx = null;
		indexByUHostPortIdx = null;
	}

	public CFIntServiceTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecServiceObj>();
		allService = null;
		indexByClusterIdx = null;
		indexByHostIdx = null;
		indexByTypeIdx = null;
		indexByUTypeIdx = null;
		indexByUHostPortIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecServiceTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecServiceTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecServiceTableObj.getRuntimeClassCode() );
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
		allService = null;
		indexByClusterIdx = null;
		indexByHostIdx = null;
		indexByTypeIdx = null;
		indexByUTypeIdx = null;
		indexByUHostPortIdx = null;
		List<ICFSecServiceObj> toForget = new LinkedList<ICFSecServiceObj>();
		ICFSecServiceObj cur = null;
		Iterator<ICFSecServiceObj> iter = members.values().iterator();
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
	 *	CFIntServiceObj.
	 */
	@Override
	public ICFSecServiceObj newInstance() {
		ICFSecServiceObj inst = new CFIntServiceObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntServiceObj.
	 */
	@Override
	public ICFSecServiceEditObj newEditInstance( ICFSecServiceObj orig ) {
		ICFSecServiceEditObj edit = new CFIntServiceEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecServiceObj realiseService( ICFSecServiceObj Obj ) {
		ICFSecServiceObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecServiceObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecServiceObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecServiceByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByHostIdx != null ) {
				ICFSecServiceByHostIdxKey keyHostIdx =
					schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
				keyHostIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapHostIdx = indexByHostIdx.get( keyHostIdx );
				if( mapHostIdx != null ) {
					mapHostIdx.remove( keepObj.getPKey() );
					if( mapHostIdx.size() <= 0 ) {
						indexByHostIdx.remove( keyHostIdx );
					}
				}
			}

			if( indexByTypeIdx != null ) {
				ICFSecServiceByTypeIdxKey keyTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
				keyTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapTypeIdx = indexByTypeIdx.get( keyTypeIdx );
				if( mapTypeIdx != null ) {
					mapTypeIdx.remove( keepObj.getPKey() );
					if( mapTypeIdx.size() <= 0 ) {
						indexByTypeIdx.remove( keyTypeIdx );
					}
				}
			}

			if( indexByUTypeIdx != null ) {
				ICFSecServiceByUTypeIdxKey keyUTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
				keyUTypeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUTypeIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				indexByUTypeIdx.remove( keyUTypeIdx );
			}

			if( indexByUHostPortIdx != null ) {
				ICFSecServiceByUHostPortIdxKey keyUHostPortIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
				keyUHostPortIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUHostPortIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUHostPortIdx.setRequiredHostPort( keepObj.getRequiredHostPort() );
				indexByUHostPortIdx.remove( keyUHostPortIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecServiceByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByHostIdx != null ) {
				ICFSecServiceByHostIdxKey keyHostIdx =
					schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
				keyHostIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapHostIdx = indexByHostIdx.get( keyHostIdx );
				if( mapHostIdx != null ) {
					mapHostIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTypeIdx != null ) {
				ICFSecServiceByTypeIdxKey keyTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
				keyTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapTypeIdx = indexByTypeIdx.get( keyTypeIdx );
				if( mapTypeIdx != null ) {
					mapTypeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUTypeIdx != null ) {
				ICFSecServiceByUTypeIdxKey keyUTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
				keyUTypeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUTypeIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				indexByUTypeIdx.put( keyUTypeIdx, keepObj );
			}

			if( indexByUHostPortIdx != null ) {
				ICFSecServiceByUHostPortIdxKey keyUHostPortIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
				keyUHostPortIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUHostPortIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUHostPortIdx.setRequiredHostPort( keepObj.getRequiredHostPort() );
				indexByUHostPortIdx.put( keyUHostPortIdx, keepObj );
			}

			if( allService != null ) {
				allService.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allService != null ) {
				allService.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecServiceByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByHostIdx != null ) {
				ICFSecServiceByHostIdxKey keyHostIdx =
					schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
				keyHostIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapHostIdx = indexByHostIdx.get( keyHostIdx );
				if( mapHostIdx != null ) {
					mapHostIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTypeIdx != null ) {
				ICFSecServiceByTypeIdxKey keyTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
				keyTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				Map<CFLibDbKeyHash256, ICFSecServiceObj > mapTypeIdx = indexByTypeIdx.get( keyTypeIdx );
				if( mapTypeIdx != null ) {
					mapTypeIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUTypeIdx != null ) {
				ICFSecServiceByUTypeIdxKey keyUTypeIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
				keyUTypeIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUTypeIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUTypeIdx.setRequiredServiceTypeId( keepObj.getRequiredServiceTypeId() );
				indexByUTypeIdx.put( keyUTypeIdx, keepObj );
			}

			if( indexByUHostPortIdx != null ) {
				ICFSecServiceByUHostPortIdxKey keyUHostPortIdx =
					schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
				keyUHostPortIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUHostPortIdx.setRequiredHostNodeId( keepObj.getRequiredHostNodeId() );
				keyUHostPortIdx.setRequiredHostPort( keepObj.getRequiredHostPort() );
				indexByUHostPortIdx.put( keyUHostPortIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecServiceObj createService( ICFSecServiceObj Obj ) {
		ICFSecServiceObj obj = Obj;
		ICFSecService rec = obj.getServiceRec();
		schema.getCFSecBackingStore().getTableService().createService(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecServiceObj readService( CFLibDbKeyHash256 pkey ) {
		return( readService( pkey, false ) );
	}

	@Override
	public ICFSecServiceObj readService( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecServiceObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecService readRec = schema.getCFSecBackingStore().getTableService().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getServiceTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecServiceObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecServiceObj readCachedService( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeService( ICFSecServiceObj obj )
	{
		final String S_ProcName = "CFIntServiceTableObj.reallyDeepDisposeService() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecServiceObj existing = readCachedService( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecServiceByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecServiceByHostIdxKey keyHostIdx = schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
		keyHostIdx.setRequiredHostNodeId( existing.getRequiredHostNodeId() );

		ICFSecServiceByTypeIdxKey keyTypeIdx = schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
		keyTypeIdx.setRequiredServiceTypeId( existing.getRequiredServiceTypeId() );

		ICFSecServiceByUTypeIdxKey keyUTypeIdx = schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
		keyUTypeIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUTypeIdx.setRequiredHostNodeId( existing.getRequiredHostNodeId() );
		keyUTypeIdx.setRequiredServiceTypeId( existing.getRequiredServiceTypeId() );

		ICFSecServiceByUHostPortIdxKey keyUHostPortIdx = schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
		keyUHostPortIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUHostPortIdx.setRequiredHostNodeId( existing.getRequiredHostNodeId() );
		keyUHostPortIdx.setRequiredHostPort( existing.getRequiredHostPort() );



		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}

		if( indexByHostIdx != null ) {
			if( indexByHostIdx.containsKey( keyHostIdx ) ) {
				indexByHostIdx.get( keyHostIdx ).remove( pkey );
				if( indexByHostIdx.get( keyHostIdx ).size() <= 0 ) {
					indexByHostIdx.remove( keyHostIdx );
				}
			}
		}

		if( indexByTypeIdx != null ) {
			if( indexByTypeIdx.containsKey( keyTypeIdx ) ) {
				indexByTypeIdx.get( keyTypeIdx ).remove( pkey );
				if( indexByTypeIdx.get( keyTypeIdx ).size() <= 0 ) {
					indexByTypeIdx.remove( keyTypeIdx );
				}
			}
		}

		if( indexByUTypeIdx != null ) {
			indexByUTypeIdx.remove( keyUTypeIdx );
		}

		if( indexByUHostPortIdx != null ) {
			indexByUHostPortIdx.remove( keyUHostPortIdx );
		}


	}
	@Override
	public void deepDisposeService( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceObj obj = readCachedService( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecServiceObj lockService( CFLibDbKeyHash256 pkey ) {
		ICFSecServiceObj locked = null;
		ICFSecService lockRec = schema.getCFSecBackingStore().getTableService().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getServiceTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecServiceObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockService", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecServiceObj> readAllService() {
		return( readAllService( false ) );
	}

	@Override
	public List<ICFSecServiceObj> readAllService( boolean forceRead ) {
		final String S_ProcName = "readAllService";
		if( ( allService == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> map = new HashMap<CFLibDbKeyHash256,ICFSecServiceObj>();
			allService = map;
			ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().readAllDerived( null );
			ICFSecService rec;
			ICFSecServiceObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
			}
		}
		int len = allService.size();
		ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
		Iterator<ICFSecServiceObj> valIter = allService.values().iterator();
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
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
		List<ICFSecServiceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecServiceObj> readCachedAllService() {
		final String S_ProcName = "readCachedAllService";
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>();
		if( allService != null ) {
			int len = allService.size();
			ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
			Iterator<ICFSecServiceObj> valIter = allService.values().iterator();
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
		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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

	/**
	 *	Return a sorted map of a page of the Service-derived instances in the database.
	 *
	 *	@return	List of ICFSecServiceObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecServiceObj> pageAllService(CFLibDbKeyHash256 priorServiceId )
	{
		final String S_ProcName = "pageAllService";
		Map<CFLibDbKeyHash256, ICFSecServiceObj> map = new HashMap<CFLibDbKeyHash256,ICFSecServiceObj>();
		ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().pageAllRec( null,
			priorServiceId );
		ICFSecService rec;
		ICFSecServiceObj obj;
		ICFSecServiceObj realised;
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecServiceObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecServiceObj readServiceByIdIdx( CFLibDbKeyHash256 ServiceId )
	{
		return( readServiceByIdIdx( ServiceId,
			false ) );
	}

	@Override
	public ICFSecServiceObj readServiceByIdIdx( CFLibDbKeyHash256 ServiceId, boolean forceRead )
	{
		ICFSecServiceObj obj = readService( ServiceId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readServiceByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readServiceByClusterIdx";
		ICFSecServiceByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecServiceByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecServiceObj>();
			ICFSecServiceObj obj;
			ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecService rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
		Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
		List<ICFSecServiceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByHostIdx( CFLibDbKeyHash256 HostNodeId )
	{
		return( readServiceByHostIdx( HostNodeId,
			false ) );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByHostIdx( CFLibDbKeyHash256 HostNodeId,
		boolean forceRead )
	{
		final String S_ProcName = "readServiceByHostIdx";
		ICFSecServiceByHostIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
		key.setRequiredHostNodeId( HostNodeId );
		Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
		if( indexByHostIdx == null ) {
			indexByHostIdx = new HashMap< ICFSecServiceByHostIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( ( ! forceRead ) && indexByHostIdx.containsKey( key ) ) {
			dict = indexByHostIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecServiceObj>();
			ICFSecServiceObj obj;
			ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().readDerivedByHostIdx( null,
				HostNodeId );
			ICFSecService rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByHostIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
		Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
		List<ICFSecServiceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		return( readServiceByTypeIdx( ServiceTypeId,
			false ) );
	}

	@Override
	public List<ICFSecServiceObj> readServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId,
		boolean forceRead )
	{
		final String S_ProcName = "readServiceByTypeIdx";
		ICFSecServiceByTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
		key.setRequiredServiceTypeId( ServiceTypeId );
		Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
		if( indexByTypeIdx == null ) {
			indexByTypeIdx = new HashMap< ICFSecServiceByTypeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( ( ! forceRead ) && indexByTypeIdx.containsKey( key ) ) {
			dict = indexByTypeIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecServiceObj>();
			ICFSecServiceObj obj;
			ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().readDerivedByTypeIdx( null,
				ServiceTypeId );
			ICFSecService rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTypeIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
		Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
		List<ICFSecServiceObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecServiceObj readServiceByUTypeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 ServiceTypeId )
	{
		return( readServiceByUTypeIdx( ClusterId,
			HostNodeId,
			ServiceTypeId,
			false ) );
	}

	@Override
	public ICFSecServiceObj readServiceByUTypeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 ServiceTypeId, boolean forceRead )
	{
		if( indexByUTypeIdx == null ) {
			indexByUTypeIdx = new HashMap< ICFSecServiceByUTypeIdxKey,
				ICFSecServiceObj >();
		}
		ICFSecServiceByUTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredServiceTypeId( ServiceTypeId );
		ICFSecServiceObj obj = null;
		if( ( ! forceRead ) && indexByUTypeIdx.containsKey( key ) ) {
			obj = indexByUTypeIdx.get( key );
		}
		else {
			ICFSecService rec = schema.getCFSecBackingStore().getTableService().readDerivedByUTypeIdx( null,
				ClusterId,
				HostNodeId,
				ServiceTypeId );
			if( rec != null ) {
				obj = schema.getServiceTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecServiceObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecServiceObj readServiceByUHostPortIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		short HostPort )
	{
		return( readServiceByUHostPortIdx( ClusterId,
			HostNodeId,
			HostPort,
			false ) );
	}

	@Override
	public ICFSecServiceObj readServiceByUHostPortIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		short HostPort, boolean forceRead )
	{
		if( indexByUHostPortIdx == null ) {
			indexByUHostPortIdx = new HashMap< ICFSecServiceByUHostPortIdxKey,
				ICFSecServiceObj >();
		}
		ICFSecServiceByUHostPortIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredHostPort( HostPort );
		ICFSecServiceObj obj = null;
		if( ( ! forceRead ) && indexByUHostPortIdx.containsKey( key ) ) {
			obj = indexByUHostPortIdx.get( key );
		}
		else {
			ICFSecService rec = schema.getCFSecBackingStore().getTableService().readDerivedByUHostPortIdx( null,
				ClusterId,
				HostNodeId,
				HostPort );
			if( rec != null ) {
				obj = schema.getServiceTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecServiceObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecServiceObj readCachedServiceByIdIdx( CFLibDbKeyHash256 ServiceId )
	{
		ICFSecServiceObj obj = null;
		obj = readCachedService( ServiceId );
		return( obj );
	}

	@Override
	public List<ICFSecServiceObj> readCachedServiceByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedServiceByClusterIdx";
		ICFSecServiceByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
				Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
			ICFSecServiceObj obj;
			Iterator<ICFSecServiceObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
	public List<ICFSecServiceObj> readCachedServiceByHostIdx( CFLibDbKeyHash256 HostNodeId )
	{
		final String S_ProcName = "readCachedServiceByHostIdx";
		ICFSecServiceByHostIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
		key.setRequiredHostNodeId( HostNodeId );
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>();
		if( indexByHostIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
			if( indexByHostIdx.containsKey( key ) ) {
				dict = indexByHostIdx.get( key );
				int len = dict.size();
				ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
				Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
			ICFSecServiceObj obj;
			Iterator<ICFSecServiceObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
	public List<ICFSecServiceObj> readCachedServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		final String S_ProcName = "readCachedServiceByTypeIdx";
		ICFSecServiceByTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
		key.setRequiredServiceTypeId( ServiceTypeId );
		ArrayList<ICFSecServiceObj> arrayList = new ArrayList<ICFSecServiceObj>();
		if( indexByTypeIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict;
			if( indexByTypeIdx.containsKey( key ) ) {
				dict = indexByTypeIdx.get( key );
				int len = dict.size();
				ICFSecServiceObj arr[] = new ICFSecServiceObj[len];
				Iterator<ICFSecServiceObj> valIter = dict.values().iterator();
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
			ICFSecServiceObj obj;
			Iterator<ICFSecServiceObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecServiceObj> cmp = new Comparator<ICFSecServiceObj>() {
			@Override
			public int compare( ICFSecServiceObj lhs, ICFSecServiceObj rhs ) {
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
	public ICFSecServiceObj readCachedServiceByUTypeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceObj obj = null;
		ICFSecServiceByUTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredServiceTypeId( ServiceTypeId );
		if( indexByUTypeIdx != null ) {
			if( indexByUTypeIdx.containsKey( key ) ) {
				obj = indexByUTypeIdx.get( key );
			}
			else {
				Iterator<ICFSecServiceObj> valIter = members.values().iterator();
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
			Iterator<ICFSecServiceObj> valIter = members.values().iterator();
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
	public ICFSecServiceObj readCachedServiceByUHostPortIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		short HostPort )
	{
		ICFSecServiceObj obj = null;
		ICFSecServiceByUHostPortIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredHostPort( HostPort );
		if( indexByUHostPortIdx != null ) {
			if( indexByUHostPortIdx.containsKey( key ) ) {
				obj = indexByUHostPortIdx.get( key );
			}
			else {
				Iterator<ICFSecServiceObj> valIter = members.values().iterator();
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
			Iterator<ICFSecServiceObj> valIter = members.values().iterator();
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
	public void deepDisposeServiceByIdIdx( CFLibDbKeyHash256 ServiceId )
	{
		ICFSecServiceObj obj = readCachedServiceByIdIdx( ServiceId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeServiceByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeServiceByClusterIdx";
		ICFSecServiceObj obj;
		List<ICFSecServiceObj> arrayList = readCachedServiceByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecServiceObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeServiceByHostIdx( CFLibDbKeyHash256 HostNodeId )
	{
		final String S_ProcName = "deepDisposeServiceByHostIdx";
		ICFSecServiceObj obj;
		List<ICFSecServiceObj> arrayList = readCachedServiceByHostIdx( HostNodeId );
		if( arrayList != null )  {
			Iterator<ICFSecServiceObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		final String S_ProcName = "deepDisposeServiceByTypeIdx";
		ICFSecServiceObj obj;
		List<ICFSecServiceObj> arrayList = readCachedServiceByTypeIdx( ServiceTypeId );
		if( arrayList != null )  {
			Iterator<ICFSecServiceObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeServiceByUTypeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceObj obj = readCachedServiceByUTypeIdx( ClusterId,
				HostNodeId,
				ServiceTypeId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeServiceByUHostPortIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		short HostPort )
	{
		ICFSecServiceObj obj = readCachedServiceByUHostPortIdx( ClusterId,
				HostNodeId,
				HostPort );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of Service-derived instances sorted by their primary keys,
	 *	as identified by the duplicate ClusterIdx key attributes.
	 *
	 *	@param	ClusterId	The Service key attribute of the instance generating the id.
	 *
	 *	@return	A List of Service-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecServiceObj> pageServiceByClusterIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 priorServiceId )
	{
		final String S_ProcName = "pageServiceByClusterIdx";
		ICFSecServiceByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		List<ICFSecServiceObj> retList = new LinkedList<ICFSecServiceObj>();
		ICFSecServiceObj obj;
		ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().pageRecByClusterIdx( null,
				ClusterId,
			priorServiceId );
		ICFSecService rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of Service-derived instances sorted by their primary keys,
	 *	as identified by the duplicate HostIdx key attributes.
	 *
	 *	@param	HostNodeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return	A List of Service-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecServiceObj> pageServiceByHostIdx( CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 priorServiceId )
	{
		final String S_ProcName = "pageServiceByHostIdx";
		ICFSecServiceByHostIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
		key.setRequiredHostNodeId( HostNodeId );
		List<ICFSecServiceObj> retList = new LinkedList<ICFSecServiceObj>();
		ICFSecServiceObj obj;
		ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().pageRecByHostIdx( null,
				HostNodeId,
			priorServiceId );
		ICFSecService rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	/**
	 *	Read a page of data as a List of Service-derived instances sorted by their primary keys,
	 *	as identified by the duplicate TypeIdx key attributes.
	 *
	 *	@param	ServiceTypeId	The Service key attribute of the instance generating the id.
	 *
	 *	@return	A List of Service-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecServiceObj> pageServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId,
		CFLibDbKeyHash256 priorServiceId )
	{
		final String S_ProcName = "pageServiceByTypeIdx";
		ICFSecServiceByTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
		key.setRequiredServiceTypeId( ServiceTypeId );
		List<ICFSecServiceObj> retList = new LinkedList<ICFSecServiceObj>();
		ICFSecServiceObj obj;
		ICFSecService[] recList = schema.getCFSecBackingStore().getTableService().pageRecByTypeIdx( null,
				ServiceTypeId,
			priorServiceId );
		ICFSecService rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getServiceTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecServiceObj realised = (ICFSecServiceObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecServiceObj updateService( ICFSecServiceObj Obj ) {
		ICFSecServiceObj obj = Obj;
		schema.getCFSecBackingStore().getTableService().updateService( null,
			Obj.getServiceRec() );
		obj = (ICFSecServiceObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteService( ICFSecServiceObj Obj ) {
		ICFSecServiceObj obj = Obj;
		schema.getCFSecBackingStore().getTableService().deleteService( null,
			obj.getServiceRec() );
		Obj.forget();
	}

	@Override
	public void deleteServiceByIdIdx( CFLibDbKeyHash256 ServiceId )
	{
		ICFSecServiceObj obj = readService(ServiceId);
		if( obj != null ) {
			ICFSecServiceEditObj editObj = (ICFSecServiceEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecServiceEditObj)obj.beginEdit();
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
		deepDisposeServiceByIdIdx( ServiceId );
	}

	@Override
	public void deleteServiceByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecServiceByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecServiceByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableService().deleteServiceByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecServiceObj> iter = dict.values().iterator();
			ICFSecServiceObj obj;
			List<ICFSecServiceObj> toForget = new LinkedList<ICFSecServiceObj>();
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
			schema.getCFSecBackingStore().getTableService().deleteServiceByClusterIdx( null,
				ClusterId );
		}
		deepDisposeServiceByClusterIdx( ClusterId );
	}

	@Override
	public void deleteServiceByHostIdx( CFLibDbKeyHash256 HostNodeId )
	{
		ICFSecServiceByHostIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByHostIdxKey();
		key.setRequiredHostNodeId( HostNodeId );
		if( indexByHostIdx == null ) {
			indexByHostIdx = new HashMap< ICFSecServiceByHostIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( indexByHostIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict = indexByHostIdx.get( key );
			schema.getCFSecBackingStore().getTableService().deleteServiceByHostIdx( null,
				HostNodeId );
			Iterator<ICFSecServiceObj> iter = dict.values().iterator();
			ICFSecServiceObj obj;
			List<ICFSecServiceObj> toForget = new LinkedList<ICFSecServiceObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByHostIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableService().deleteServiceByHostIdx( null,
				HostNodeId );
		}
		deepDisposeServiceByHostIdx( HostNodeId );
	}

	@Override
	public void deleteServiceByTypeIdx( CFLibDbKeyHash256 ServiceTypeId )
	{
		ICFSecServiceByTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByTypeIdxKey();
		key.setRequiredServiceTypeId( ServiceTypeId );
		if( indexByTypeIdx == null ) {
			indexByTypeIdx = new HashMap< ICFSecServiceByTypeIdxKey,
				Map< CFLibDbKeyHash256, ICFSecServiceObj > >();
		}
		if( indexByTypeIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecServiceObj> dict = indexByTypeIdx.get( key );
			schema.getCFSecBackingStore().getTableService().deleteServiceByTypeIdx( null,
				ServiceTypeId );
			Iterator<ICFSecServiceObj> iter = dict.values().iterator();
			ICFSecServiceObj obj;
			List<ICFSecServiceObj> toForget = new LinkedList<ICFSecServiceObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTypeIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableService().deleteServiceByTypeIdx( null,
				ServiceTypeId );
		}
		deepDisposeServiceByTypeIdx( ServiceTypeId );
	}

	@Override
	public void deleteServiceByUTypeIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		CFLibDbKeyHash256 ServiceTypeId )
	{
		if( indexByUTypeIdx == null ) {
			indexByUTypeIdx = new HashMap< ICFSecServiceByUTypeIdxKey,
				ICFSecServiceObj >();
		}
		ICFSecServiceByUTypeIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUTypeIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredServiceTypeId( ServiceTypeId );
		ICFSecServiceObj obj = null;
		if( indexByUTypeIdx.containsKey( key ) ) {
			obj = indexByUTypeIdx.get( key );
			schema.getCFSecBackingStore().getTableService().deleteServiceByUTypeIdx( null,
				ClusterId,
				HostNodeId,
				ServiceTypeId );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableService().deleteServiceByUTypeIdx( null,
				ClusterId,
				HostNodeId,
				ServiceTypeId );
		}
		deepDisposeServiceByUTypeIdx( ClusterId,
				HostNodeId,
				ServiceTypeId );
	}

	@Override
	public void deleteServiceByUHostPortIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 HostNodeId,
		short HostPort )
	{
		if( indexByUHostPortIdx == null ) {
			indexByUHostPortIdx = new HashMap< ICFSecServiceByUHostPortIdxKey,
				ICFSecServiceObj >();
		}
		ICFSecServiceByUHostPortIdxKey key = schema.getCFSecBackingStore().getFactoryService().newByUHostPortIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostNodeId( HostNodeId );
		key.setRequiredHostPort( HostPort );
		ICFSecServiceObj obj = null;
		if( indexByUHostPortIdx.containsKey( key ) ) {
			obj = indexByUHostPortIdx.get( key );
			schema.getCFSecBackingStore().getTableService().deleteServiceByUHostPortIdx( null,
				ClusterId,
				HostNodeId,
				HostPort );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableService().deleteServiceByUHostPortIdx( null,
				ClusterId,
				HostNodeId,
				HostPort );
		}
		deepDisposeServiceByUHostPortIdx( ClusterId,
				HostNodeId,
				HostPort );
	}
}