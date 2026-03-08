// Description: Java 25 Table Object implementation for HostNode.

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

public class CFIntHostNodeTableObj
	implements ICFIntHostNodeTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<CFLibDbKeyHash256, ICFSecHostNodeObj> members;
	private Map<CFLibDbKeyHash256, ICFSecHostNodeObj> allHostNode;
	private Map< ICFSecHostNodeByClusterIdxKey,
		Map<CFLibDbKeyHash256, ICFSecHostNodeObj > > indexByClusterIdx;
	private Map< ICFSecHostNodeByUDescrIdxKey,
		ICFSecHostNodeObj > indexByUDescrIdx;
	private Map< ICFSecHostNodeByHostNameIdxKey,
		ICFSecHostNodeObj > indexByHostNameIdx;
	public static String TABLE_NAME = "HostNode";
	public static String TABLE_DBNAME = "hostnode";

	public CFIntHostNodeTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFSecHostNodeObj>();
		allHostNode = null;
		indexByClusterIdx = null;
		indexByUDescrIdx = null;
		indexByHostNameIdx = null;
	}

	public CFIntHostNodeTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFSecHostNodeObj>();
		allHostNode = null;
		indexByClusterIdx = null;
		indexByUDescrIdx = null;
		indexByHostNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecHostNodeTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecHostNodeTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecHostNodeTableObj.getRuntimeClassCode() );
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
		allHostNode = null;
		indexByClusterIdx = null;
		indexByUDescrIdx = null;
		indexByHostNameIdx = null;
		List<ICFSecHostNodeObj> toForget = new LinkedList<ICFSecHostNodeObj>();
		ICFSecHostNodeObj cur = null;
		Iterator<ICFSecHostNodeObj> iter = members.values().iterator();
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
	 *	CFIntHostNodeObj.
	 */
	@Override
	public ICFSecHostNodeObj newInstance() {
		ICFSecHostNodeObj inst = new CFIntHostNodeObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntHostNodeObj.
	 */
	@Override
	public ICFSecHostNodeEditObj newEditInstance( ICFSecHostNodeObj orig ) {
		ICFSecHostNodeEditObj edit = new CFIntHostNodeEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecHostNodeObj realiseHostNode( ICFSecHostNodeObj Obj ) {
		ICFSecHostNodeObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecHostNodeObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecHostNodeObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByClusterIdx != null ) {
				ICFSecHostNodeByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecHostNodeObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.remove( keepObj.getPKey() );
					if( mapClusterIdx.size() <= 0 ) {
						indexByClusterIdx.remove( keyClusterIdx );
					}
				}
			}

			if( indexByUDescrIdx != null ) {
				ICFSecHostNodeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.remove( keyUDescrIdx );
			}

			if( indexByHostNameIdx != null ) {
				ICFSecHostNodeByHostNameIdxKey keyHostNameIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
				keyHostNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyHostNameIdx.setRequiredHostName( keepObj.getRequiredHostName() );
				indexByHostNameIdx.remove( keyHostNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByClusterIdx != null ) {
				ICFSecHostNodeByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecHostNodeObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUDescrIdx != null ) {
				ICFSecHostNodeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

			if( indexByHostNameIdx != null ) {
				ICFSecHostNodeByHostNameIdxKey keyHostNameIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
				keyHostNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyHostNameIdx.setRequiredHostName( keepObj.getRequiredHostName() );
				indexByHostNameIdx.put( keyHostNameIdx, keepObj );
			}

			if( allHostNode != null ) {
				allHostNode.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allHostNode != null ) {
				allHostNode.put( keepObj.getPKey(), keepObj );
			}

			if( indexByClusterIdx != null ) {
				ICFSecHostNodeByClusterIdxKey keyClusterIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
				keyClusterIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				Map<CFLibDbKeyHash256, ICFSecHostNodeObj > mapClusterIdx = indexByClusterIdx.get( keyClusterIdx );
				if( mapClusterIdx != null ) {
					mapClusterIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByUDescrIdx != null ) {
				ICFSecHostNodeByUDescrIdxKey keyUDescrIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
				keyUDescrIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyUDescrIdx.setRequiredDescription( keepObj.getRequiredDescription() );
				indexByUDescrIdx.put( keyUDescrIdx, keepObj );
			}

			if( indexByHostNameIdx != null ) {
				ICFSecHostNodeByHostNameIdxKey keyHostNameIdx =
					schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
				keyHostNameIdx.setRequiredClusterId( keepObj.getRequiredClusterId() );
				keyHostNameIdx.setRequiredHostName( keepObj.getRequiredHostName() );
				indexByHostNameIdx.put( keyHostNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecHostNodeObj createHostNode( ICFSecHostNodeObj Obj ) {
		ICFSecHostNodeObj obj = Obj;
		ICFSecHostNode rec = obj.getHostNodeRec();
		schema.getCFSecBackingStore().getTableHostNode().createHostNode(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecHostNodeObj readHostNode( CFLibDbKeyHash256 pkey ) {
		return( readHostNode( pkey, false ) );
	}

	@Override
	public ICFSecHostNodeObj readHostNode( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFSecHostNodeObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecHostNode readRec = schema.getCFSecBackingStore().getTableHostNode().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getHostNodeTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecHostNodeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecHostNodeObj readCachedHostNode( CFLibDbKeyHash256 pkey ) {
		ICFSecHostNodeObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeHostNode( ICFSecHostNodeObj obj )
	{
		final String S_ProcName = "CFIntHostNodeTableObj.reallyDeepDisposeHostNode() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFSecHostNodeObj existing = readCachedHostNode( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecHostNodeByClusterIdxKey keyClusterIdx = schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
		keyClusterIdx.setRequiredClusterId( existing.getRequiredClusterId() );

		ICFSecHostNodeByUDescrIdxKey keyUDescrIdx = schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
		keyUDescrIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyUDescrIdx.setRequiredDescription( existing.getRequiredDescription() );

		ICFSecHostNodeByHostNameIdxKey keyHostNameIdx = schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
		keyHostNameIdx.setRequiredClusterId( existing.getRequiredClusterId() );
		keyHostNameIdx.setRequiredHostName( existing.getRequiredHostName() );


					schema.getServiceTableObj().deepDisposeServiceByHostIdx( existing.getRequiredHostNodeId() );

		if( indexByClusterIdx != null ) {
			if( indexByClusterIdx.containsKey( keyClusterIdx ) ) {
				indexByClusterIdx.get( keyClusterIdx ).remove( pkey );
				if( indexByClusterIdx.get( keyClusterIdx ).size() <= 0 ) {
					indexByClusterIdx.remove( keyClusterIdx );
				}
			}
		}

		if( indexByUDescrIdx != null ) {
			indexByUDescrIdx.remove( keyUDescrIdx );
		}

		if( indexByHostNameIdx != null ) {
			indexByHostNameIdx.remove( keyHostNameIdx );
		}


	}
	@Override
	public void deepDisposeHostNode( CFLibDbKeyHash256 pkey ) {
		ICFSecHostNodeObj obj = readCachedHostNode( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecHostNodeObj lockHostNode( CFLibDbKeyHash256 pkey ) {
		ICFSecHostNodeObj locked = null;
		ICFSecHostNode lockRec = schema.getCFSecBackingStore().getTableHostNode().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getHostNodeTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecHostNodeObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockHostNode", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecHostNodeObj> readAllHostNode() {
		return( readAllHostNode( false ) );
	}

	@Override
	public List<ICFSecHostNodeObj> readAllHostNode( boolean forceRead ) {
		final String S_ProcName = "readAllHostNode";
		if( ( allHostNode == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFSecHostNodeObj> map = new HashMap<CFLibDbKeyHash256,ICFSecHostNodeObj>();
			allHostNode = map;
			ICFSecHostNode[] recList = schema.getCFSecBackingStore().getTableHostNode().readAllDerived( null );
			ICFSecHostNode rec;
			ICFSecHostNodeObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecHostNodeObj realised = (ICFSecHostNodeObj)obj.realise();
			}
		}
		int len = allHostNode.size();
		ICFSecHostNodeObj arr[] = new ICFSecHostNodeObj[len];
		Iterator<ICFSecHostNodeObj> valIter = allHostNode.values().iterator();
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
		ArrayList<ICFSecHostNodeObj> arrayList = new ArrayList<ICFSecHostNodeObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecHostNodeObj> cmp = new Comparator<ICFSecHostNodeObj>() {
			@Override
			public int compare( ICFSecHostNodeObj lhs, ICFSecHostNodeObj rhs ) {
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
		List<ICFSecHostNodeObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecHostNodeObj> readCachedAllHostNode() {
		final String S_ProcName = "readCachedAllHostNode";
		ArrayList<ICFSecHostNodeObj> arrayList = new ArrayList<ICFSecHostNodeObj>();
		if( allHostNode != null ) {
			int len = allHostNode.size();
			ICFSecHostNodeObj arr[] = new ICFSecHostNodeObj[len];
			Iterator<ICFSecHostNodeObj> valIter = allHostNode.values().iterator();
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
		Comparator<ICFSecHostNodeObj> cmp = new Comparator<ICFSecHostNodeObj>() {
			public int compare( ICFSecHostNodeObj lhs, ICFSecHostNodeObj rhs ) {
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
	 *	Return a sorted map of a page of the HostNode-derived instances in the database.
	 *
	 *	@return	List of ICFSecHostNodeObj instance, sorted by their primary keys, which
	 *		may include an empty set.
	 */
	@Override
	public List<ICFSecHostNodeObj> pageAllHostNode(CFLibDbKeyHash256 priorHostNodeId )
	{
		final String S_ProcName = "pageAllHostNode";
		Map<CFLibDbKeyHash256, ICFSecHostNodeObj> map = new HashMap<CFLibDbKeyHash256,ICFSecHostNodeObj>();
		ICFSecHostNode[] recList = schema.getCFSecBackingStore().getTableHostNode().pageAllRec( null,
			priorHostNodeId );
		ICFSecHostNode rec;
		ICFSecHostNodeObj obj;
		ICFSecHostNodeObj realised;
		ArrayList<ICFSecHostNodeObj> arrayList = new ArrayList<ICFSecHostNodeObj>( recList.length );
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			realised = (ICFSecHostNodeObj)obj.realise();
			arrayList.add( realised );
		}
		return( arrayList );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByIdIdx( CFLibDbKeyHash256 HostNodeId )
	{
		return( readHostNodeByIdIdx( HostNodeId,
			false ) );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByIdIdx( CFLibDbKeyHash256 HostNodeId, boolean forceRead )
	{
		ICFSecHostNodeObj obj = readHostNode( HostNodeId, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecHostNodeObj> readHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		return( readHostNodeByClusterIdx( ClusterId,
			false ) );
	}

	@Override
	public List<ICFSecHostNodeObj> readHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId,
		boolean forceRead )
	{
		final String S_ProcName = "readHostNodeByClusterIdx";
		ICFSecHostNodeByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		Map<CFLibDbKeyHash256, ICFSecHostNodeObj> dict;
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecHostNodeByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecHostNodeObj > >();
		}
		if( ( ! forceRead ) && indexByClusterIdx.containsKey( key ) ) {
			dict = indexByClusterIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFSecHostNodeObj>();
			ICFSecHostNodeObj obj;
			ICFSecHostNode[] recList = schema.getCFSecBackingStore().getTableHostNode().readDerivedByClusterIdx( null,
				ClusterId );
			ICFSecHostNode rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getHostNodeTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecHostNodeObj realised = (ICFSecHostNodeObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByClusterIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecHostNodeObj arr[] = new ICFSecHostNodeObj[len];
		Iterator<ICFSecHostNodeObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecHostNodeObj> arrayList = new ArrayList<ICFSecHostNodeObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecHostNodeObj> cmp = new Comparator<ICFSecHostNodeObj>() {
			@Override
			public int compare( ICFSecHostNodeObj lhs, ICFSecHostNodeObj rhs ) {
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
		List<ICFSecHostNodeObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByUDescrIdx( CFLibDbKeyHash256 ClusterId,
		String Description )
	{
		return( readHostNodeByUDescrIdx( ClusterId,
			Description,
			false ) );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByUDescrIdx( CFLibDbKeyHash256 ClusterId,
		String Description, boolean forceRead )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecHostNodeByUDescrIdxKey,
				ICFSecHostNodeObj >();
		}
		ICFSecHostNodeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredDescription( Description );
		ICFSecHostNodeObj obj = null;
		if( ( ! forceRead ) && indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
		}
		else {
			ICFSecHostNode rec = schema.getCFSecBackingStore().getTableHostNode().readDerivedByUDescrIdx( null,
				ClusterId,
				Description );
			if( rec != null ) {
				obj = schema.getHostNodeTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecHostNodeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByHostNameIdx( CFLibDbKeyHash256 ClusterId,
		String HostName )
	{
		return( readHostNodeByHostNameIdx( ClusterId,
			HostName,
			false ) );
	}

	@Override
	public ICFSecHostNodeObj readHostNodeByHostNameIdx( CFLibDbKeyHash256 ClusterId,
		String HostName, boolean forceRead )
	{
		if( indexByHostNameIdx == null ) {
			indexByHostNameIdx = new HashMap< ICFSecHostNodeByHostNameIdxKey,
				ICFSecHostNodeObj >();
		}
		ICFSecHostNodeByHostNameIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostName( HostName );
		ICFSecHostNodeObj obj = null;
		if( ( ! forceRead ) && indexByHostNameIdx.containsKey( key ) ) {
			obj = indexByHostNameIdx.get( key );
		}
		else {
			ICFSecHostNode rec = schema.getCFSecBackingStore().getTableHostNode().readDerivedByHostNameIdx( null,
				ClusterId,
				HostName );
			if( rec != null ) {
				obj = schema.getHostNodeTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecHostNodeObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecHostNodeObj readCachedHostNodeByIdIdx( CFLibDbKeyHash256 HostNodeId )
	{
		ICFSecHostNodeObj obj = null;
		obj = readCachedHostNode( HostNodeId );
		return( obj );
	}

	@Override
	public List<ICFSecHostNodeObj> readCachedHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "readCachedHostNodeByClusterIdx";
		ICFSecHostNodeByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		ArrayList<ICFSecHostNodeObj> arrayList = new ArrayList<ICFSecHostNodeObj>();
		if( indexByClusterIdx != null ) {
			Map<CFLibDbKeyHash256, ICFSecHostNodeObj> dict;
			if( indexByClusterIdx.containsKey( key ) ) {
				dict = indexByClusterIdx.get( key );
				int len = dict.size();
				ICFSecHostNodeObj arr[] = new ICFSecHostNodeObj[len];
				Iterator<ICFSecHostNodeObj> valIter = dict.values().iterator();
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
			ICFSecHostNodeObj obj;
			Iterator<ICFSecHostNodeObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecHostNodeObj> cmp = new Comparator<ICFSecHostNodeObj>() {
			@Override
			public int compare( ICFSecHostNodeObj lhs, ICFSecHostNodeObj rhs ) {
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
	public ICFSecHostNodeObj readCachedHostNodeByUDescrIdx( CFLibDbKeyHash256 ClusterId,
		String Description )
	{
		ICFSecHostNodeObj obj = null;
		ICFSecHostNodeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredDescription( Description );
		if( indexByUDescrIdx != null ) {
			if( indexByUDescrIdx.containsKey( key ) ) {
				obj = indexByUDescrIdx.get( key );
			}
			else {
				Iterator<ICFSecHostNodeObj> valIter = members.values().iterator();
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
			Iterator<ICFSecHostNodeObj> valIter = members.values().iterator();
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
	public ICFSecHostNodeObj readCachedHostNodeByHostNameIdx( CFLibDbKeyHash256 ClusterId,
		String HostName )
	{
		ICFSecHostNodeObj obj = null;
		ICFSecHostNodeByHostNameIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostName( HostName );
		if( indexByHostNameIdx != null ) {
			if( indexByHostNameIdx.containsKey( key ) ) {
				obj = indexByHostNameIdx.get( key );
			}
			else {
				Iterator<ICFSecHostNodeObj> valIter = members.values().iterator();
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
			Iterator<ICFSecHostNodeObj> valIter = members.values().iterator();
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
	public void deepDisposeHostNodeByIdIdx( CFLibDbKeyHash256 HostNodeId )
	{
		ICFSecHostNodeObj obj = readCachedHostNodeByIdIdx( HostNodeId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		final String S_ProcName = "deepDisposeHostNodeByClusterIdx";
		ICFSecHostNodeObj obj;
		List<ICFSecHostNodeObj> arrayList = readCachedHostNodeByClusterIdx( ClusterId );
		if( arrayList != null )  {
			Iterator<ICFSecHostNodeObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeHostNodeByUDescrIdx( CFLibDbKeyHash256 ClusterId,
		String Description )
	{
		ICFSecHostNodeObj obj = readCachedHostNodeByUDescrIdx( ClusterId,
				Description );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeHostNodeByHostNameIdx( CFLibDbKeyHash256 ClusterId,
		String HostName )
	{
		ICFSecHostNodeObj obj = readCachedHostNodeByHostNameIdx( ClusterId,
				HostName );
		if( obj != null ) {
			obj.forget();
		}
	}

	/**
	 *	Read a page of data as a List of HostNode-derived instances sorted by their primary keys,
	 *	as identified by the duplicate ClusterIdx key attributes.
	 *
	 *	@param	ClusterId	The HostNode key attribute of the instance generating the id.
	 *
	 *	@return	A List of HostNode-derived instances sorted by their primary keys,
	 *		as identified by the key attributes, which may be an empty set.
	 */
	@Override
	public List<ICFSecHostNodeObj> pageHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId,
		CFLibDbKeyHash256 priorHostNodeId )
	{
		final String S_ProcName = "pageHostNodeByClusterIdx";
		ICFSecHostNodeByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		List<ICFSecHostNodeObj> retList = new LinkedList<ICFSecHostNodeObj>();
		ICFSecHostNodeObj obj;
		ICFSecHostNode[] recList = schema.getCFSecBackingStore().getTableHostNode().pageRecByClusterIdx( null,
				ClusterId,
			priorHostNodeId );
		ICFSecHostNode rec;
		for( int idx = 0; idx < recList.length; idx ++ ) {
			rec = recList[ idx ];
				obj = schema.getHostNodeTableObj().newInstance();
			obj.setPKey( rec.getPKey() );
			obj.setRec( rec );
			ICFSecHostNodeObj realised = (ICFSecHostNodeObj)obj.realise();
			retList.add( realised );
		}
		return( retList );
	}

	@Override
	public ICFSecHostNodeObj updateHostNode( ICFSecHostNodeObj Obj ) {
		ICFSecHostNodeObj obj = Obj;
		schema.getCFSecBackingStore().getTableHostNode().updateHostNode( null,
			Obj.getHostNodeRec() );
		obj = (ICFSecHostNodeObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteHostNode( ICFSecHostNodeObj Obj ) {
		ICFSecHostNodeObj obj = Obj;
		schema.getCFSecBackingStore().getTableHostNode().deleteHostNode( null,
			obj.getHostNodeRec() );
		Obj.forget();
	}

	@Override
	public void deleteHostNodeByIdIdx( CFLibDbKeyHash256 HostNodeId )
	{
		ICFSecHostNodeObj obj = readHostNode(HostNodeId);
		if( obj != null ) {
			ICFSecHostNodeEditObj editObj = (ICFSecHostNodeEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecHostNodeEditObj)obj.beginEdit();
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
		deepDisposeHostNodeByIdIdx( HostNodeId );
	}

	@Override
	public void deleteHostNodeByClusterIdx( CFLibDbKeyHash256 ClusterId )
	{
		ICFSecHostNodeByClusterIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByClusterIdxKey();
		key.setRequiredClusterId( ClusterId );
		if( indexByClusterIdx == null ) {
			indexByClusterIdx = new HashMap< ICFSecHostNodeByClusterIdxKey,
				Map< CFLibDbKeyHash256, ICFSecHostNodeObj > >();
		}
		if( indexByClusterIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFSecHostNodeObj> dict = indexByClusterIdx.get( key );
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByClusterIdx( null,
				ClusterId );
			Iterator<ICFSecHostNodeObj> iter = dict.values().iterator();
			ICFSecHostNodeObj obj;
			List<ICFSecHostNodeObj> toForget = new LinkedList<ICFSecHostNodeObj>();
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
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByClusterIdx( null,
				ClusterId );
		}
		deepDisposeHostNodeByClusterIdx( ClusterId );
	}

	@Override
	public void deleteHostNodeByUDescrIdx( CFLibDbKeyHash256 ClusterId,
		String Description )
	{
		if( indexByUDescrIdx == null ) {
			indexByUDescrIdx = new HashMap< ICFSecHostNodeByUDescrIdxKey,
				ICFSecHostNodeObj >();
		}
		ICFSecHostNodeByUDescrIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByUDescrIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredDescription( Description );
		ICFSecHostNodeObj obj = null;
		if( indexByUDescrIdx.containsKey( key ) ) {
			obj = indexByUDescrIdx.get( key );
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByUDescrIdx( null,
				ClusterId,
				Description );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByUDescrIdx( null,
				ClusterId,
				Description );
		}
		deepDisposeHostNodeByUDescrIdx( ClusterId,
				Description );
	}

	@Override
	public void deleteHostNodeByHostNameIdx( CFLibDbKeyHash256 ClusterId,
		String HostName )
	{
		if( indexByHostNameIdx == null ) {
			indexByHostNameIdx = new HashMap< ICFSecHostNodeByHostNameIdxKey,
				ICFSecHostNodeObj >();
		}
		ICFSecHostNodeByHostNameIdxKey key = schema.getCFSecBackingStore().getFactoryHostNode().newByHostNameIdxKey();
		key.setRequiredClusterId( ClusterId );
		key.setRequiredHostName( HostName );
		ICFSecHostNodeObj obj = null;
		if( indexByHostNameIdx.containsKey( key ) ) {
			obj = indexByHostNameIdx.get( key );
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByHostNameIdx( null,
				ClusterId,
				HostName );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableHostNode().deleteHostNodeByHostNameIdx( null,
				ClusterId,
				HostName );
		}
		deepDisposeHostNodeByHostNameIdx( ClusterId,
				HostName );
	}
}