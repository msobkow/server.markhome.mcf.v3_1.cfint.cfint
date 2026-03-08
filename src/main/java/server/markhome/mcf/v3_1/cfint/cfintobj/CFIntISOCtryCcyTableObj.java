// Description: Java 25 Table Object implementation for ISOCtryCcy.

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

public class CFIntISOCtryCcyTableObj
	implements ICFIntISOCtryCcyTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> members;
	private Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> allISOCtryCcy;
	private Map< ICFSecISOCtryCcyByCtryIdxKey,
		Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > > indexByCtryIdx;
	private Map< ICFSecISOCtryCcyByCcyIdxKey,
		Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > > indexByCcyIdx;
	public static String TABLE_NAME = "ISOCtryCcy";
	public static String TABLE_DBNAME = "iso_cntryccy";

	public CFIntISOCtryCcyTableObj() {
		schema = null;
		members = new HashMap<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj>();
		allISOCtryCcy = null;
		indexByCtryIdx = null;
		indexByCcyIdx = null;
	}

	public CFIntISOCtryCcyTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj>();
		allISOCtryCcy = null;
		indexByCtryIdx = null;
		indexByCcyIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecISOCtryCcyTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecISOCtryCcyTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecISOCtryCcyTableObj.getRuntimeClassCode() );
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
		allISOCtryCcy = null;
		indexByCtryIdx = null;
		indexByCcyIdx = null;
		List<ICFSecISOCtryCcyObj> toForget = new LinkedList<ICFSecISOCtryCcyObj>();
		ICFSecISOCtryCcyObj cur = null;
		Iterator<ICFSecISOCtryCcyObj> iter = members.values().iterator();
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
	 *	CFIntISOCtryCcyObj.
	 */
	@Override
	public ICFSecISOCtryCcyObj newInstance() {
		ICFSecISOCtryCcyObj inst = new CFIntISOCtryCcyObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntISOCtryCcyObj.
	 */
	@Override
	public ICFSecISOCtryCcyEditObj newEditInstance( ICFSecISOCtryCcyObj orig ) {
		ICFSecISOCtryCcyEditObj edit = new CFIntISOCtryCcyEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecISOCtryCcyObj realiseISOCtryCcy( ICFSecISOCtryCcyObj Obj ) {
		ICFSecISOCtryCcyObj obj = Obj;
		ICFSecISOCtryCcyPKey pkey = obj.getPKey();
		ICFSecISOCtryCcyObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecISOCtryCcyObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByCtryIdx != null ) {
				ICFSecISOCtryCcyByCtryIdxKey keyCtryIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
				keyCtryIdx.setRequiredISOCtryId( keepObj.getRequiredISOCtryId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCtryIdx = indexByCtryIdx.get( keyCtryIdx );
				if( mapCtryIdx != null ) {
					mapCtryIdx.remove( keepObj.getPKey() );
					if( mapCtryIdx.size() <= 0 ) {
						indexByCtryIdx.remove( keyCtryIdx );
					}
				}
			}

			if( indexByCcyIdx != null ) {
				ICFSecISOCtryCcyByCcyIdxKey keyCcyIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
				keyCcyIdx.setRequiredISOCcyId( keepObj.getRequiredISOCcyId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCcyIdx = indexByCcyIdx.get( keyCcyIdx );
				if( mapCcyIdx != null ) {
					mapCcyIdx.remove( keepObj.getPKey() );
					if( mapCcyIdx.size() <= 0 ) {
						indexByCcyIdx.remove( keyCcyIdx );
					}
				}
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByCtryIdx != null ) {
				ICFSecISOCtryCcyByCtryIdxKey keyCtryIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
				keyCtryIdx.setRequiredISOCtryId( keepObj.getRequiredISOCtryId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCtryIdx = indexByCtryIdx.get( keyCtryIdx );
				if( mapCtryIdx != null ) {
					mapCtryIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByCcyIdx != null ) {
				ICFSecISOCtryCcyByCcyIdxKey keyCcyIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
				keyCcyIdx.setRequiredISOCcyId( keepObj.getRequiredISOCcyId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCcyIdx = indexByCcyIdx.get( keyCcyIdx );
				if( mapCcyIdx != null ) {
					mapCcyIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( allISOCtryCcy != null ) {
				allISOCtryCcy.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allISOCtryCcy != null ) {
				allISOCtryCcy.put( keepObj.getPKey(), keepObj );
			}

			if( indexByCtryIdx != null ) {
				ICFSecISOCtryCcyByCtryIdxKey keyCtryIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
				keyCtryIdx.setRequiredISOCtryId( keepObj.getRequiredISOCtryId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCtryIdx = indexByCtryIdx.get( keyCtryIdx );
				if( mapCtryIdx != null ) {
					mapCtryIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByCcyIdx != null ) {
				ICFSecISOCtryCcyByCcyIdxKey keyCcyIdx =
					schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
				keyCcyIdx.setRequiredISOCcyId( keepObj.getRequiredISOCcyId() );
				Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > mapCcyIdx = indexByCcyIdx.get( keyCcyIdx );
				if( mapCcyIdx != null ) {
					mapCcyIdx.put( keepObj.getPKey(), keepObj );
				}
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecISOCtryCcyObj createISOCtryCcy( ICFSecISOCtryCcyObj Obj ) {
		ICFSecISOCtryCcyObj obj = Obj;
		ICFSecISOCtryCcy rec = obj.getISOCtryCcyRec();
		schema.getCFSecBackingStore().getTableISOCtryCcy().createISOCtryCcy(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcy( ICFSecISOCtryCcyPKey pkey ) {
		return( readISOCtryCcy( pkey, false ) );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcy( ICFSecISOCtryCcyPKey pkey, boolean forceRead ) {
		ICFSecISOCtryCcyObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecISOCtryCcy readRec = schema.getCFSecBackingStore().getTableISOCtryCcy().readDerivedByIdIdx( null,
						pkey.getRequiredISOCtryId(),
						pkey.getRequiredISOCcyId() );
			if( readRec != null ) {
				obj = schema.getISOCtryCcyTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecISOCtryCcyObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcy( short ISOCtryId,
		short ISOCcyId ) {
		return( readISOCtryCcy( ISOCtryId,
			ISOCcyId, false ) );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcy( short ISOCtryId,
		short ISOCcyId, boolean forceRead ) {
		ICFSecISOCtryCcyObj obj = null;
		ICFSecISOCtryCcy readRec = schema.getCFSecBackingStore().getTableISOCtryCcy().readDerivedByIdIdx( null,
			ISOCtryId,
			ISOCcyId );
		if( readRec != null ) {
				obj = schema.getISOCtryCcyTableObj().newInstance();
			obj.setPKey( readRec.getPKey() );
			obj.setRec( readRec );
			obj = (ICFSecISOCtryCcyObj)obj.realise();
		}
		return( obj );
	}

	@Override
	public ICFSecISOCtryCcyObj readCachedISOCtryCcy( ICFSecISOCtryCcyPKey pkey ) {
		ICFSecISOCtryCcyObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeISOCtryCcy( ICFSecISOCtryCcyObj obj )
	{
		final String S_ProcName = "CFIntISOCtryCcyTableObj.reallyDeepDisposeISOCtryCcy() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		ICFSecISOCtryCcyPKey pkey = obj.getPKey();
		ICFSecISOCtryCcyObj existing = readCachedISOCtryCcy( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecISOCtryCcyByCtryIdxKey keyCtryIdx = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
		keyCtryIdx.setRequiredISOCtryId( existing.getRequiredISOCtryId() );

		ICFSecISOCtryCcyByCcyIdxKey keyCcyIdx = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
		keyCcyIdx.setRequiredISOCcyId( existing.getRequiredISOCcyId() );



		if( indexByCtryIdx != null ) {
			if( indexByCtryIdx.containsKey( keyCtryIdx ) ) {
				indexByCtryIdx.get( keyCtryIdx ).remove( pkey );
				if( indexByCtryIdx.get( keyCtryIdx ).size() <= 0 ) {
					indexByCtryIdx.remove( keyCtryIdx );
				}
			}
		}

		if( indexByCcyIdx != null ) {
			if( indexByCcyIdx.containsKey( keyCcyIdx ) ) {
				indexByCcyIdx.get( keyCcyIdx ).remove( pkey );
				if( indexByCcyIdx.get( keyCcyIdx ).size() <= 0 ) {
					indexByCcyIdx.remove( keyCcyIdx );
				}
			}
		}


	}
	@Override
	public void deepDisposeISOCtryCcy( ICFSecISOCtryCcyPKey pkey ) {
		ICFSecISOCtryCcyObj obj = readCachedISOCtryCcy( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecISOCtryCcyObj lockISOCtryCcy( ICFSecISOCtryCcyPKey pkey ) {
		ICFSecISOCtryCcyObj locked = null;
		ICFSecISOCtryCcy lockRec = schema.getCFSecBackingStore().getTableISOCtryCcy().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getISOCtryCcyTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecISOCtryCcyObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockISOCtryCcy", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readAllISOCtryCcy() {
		return( readAllISOCtryCcy( false ) );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readAllISOCtryCcy( boolean forceRead ) {
		final String S_ProcName = "readAllISOCtryCcy";
		if( ( allISOCtryCcy == null ) || forceRead ) {
			Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> map = new HashMap<ICFSecISOCtryCcyPKey,ICFSecISOCtryCcyObj>();
			allISOCtryCcy = map;
			ICFSecISOCtryCcy[] recList = schema.getCFSecBackingStore().getTableISOCtryCcy().readAllDerived( null );
			ICFSecISOCtryCcy rec;
			ICFSecISOCtryCcyObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecISOCtryCcyObj realised = (ICFSecISOCtryCcyObj)obj.realise();
			}
		}
		int len = allISOCtryCcy.size();
		ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
		Iterator<ICFSecISOCtryCcyObj> valIter = allISOCtryCcy.values().iterator();
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
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			@Override
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecISOCtryCcyObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readCachedAllISOCtryCcy() {
		final String S_ProcName = "readCachedAllISOCtryCcy";
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>();
		if( allISOCtryCcy != null ) {
			int len = allISOCtryCcy.size();
			ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
			Iterator<ICFSecISOCtryCcyObj> valIter = allISOCtryCcy.values().iterator();
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
		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcyByIdIdx( short ISOCtryId,
		short ISOCcyId )
	{
		return( readISOCtryCcyByIdIdx( ISOCtryId,
			ISOCcyId,
			false ) );
	}

	@Override
	public ICFSecISOCtryCcyObj readISOCtryCcyByIdIdx( short ISOCtryId,
		short ISOCcyId, boolean forceRead )
	{
		ICFSecISOCtryCcyPKey pkey = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newPKey();
		pkey.setRequiredContainerCtry(ISOCtryId);
		pkey.setRequiredParentCcy(ISOCcyId);
		ICFSecISOCtryCcyObj obj = readISOCtryCcy( pkey, forceRead );
		return( obj );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readISOCtryCcyByCtryIdx( short ISOCtryId )
	{
		return( readISOCtryCcyByCtryIdx( ISOCtryId,
			false ) );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readISOCtryCcyByCtryIdx( short ISOCtryId,
		boolean forceRead )
	{
		final String S_ProcName = "readISOCtryCcyByCtryIdx";
		ICFSecISOCtryCcyByCtryIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
		key.setRequiredISOCtryId( ISOCtryId );
		Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict;
		if( indexByCtryIdx == null ) {
			indexByCtryIdx = new HashMap< ICFSecISOCtryCcyByCtryIdxKey,
				Map< ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > >();
		}
		if( ( ! forceRead ) && indexByCtryIdx.containsKey( key ) ) {
			dict = indexByCtryIdx.get( key );
		}
		else {
			dict = new HashMap<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj>();
			ICFSecISOCtryCcyObj obj;
			ICFSecISOCtryCcy[] recList = schema.getCFSecBackingStore().getTableISOCtryCcy().readDerivedByCtryIdx( null,
				ISOCtryId );
			ICFSecISOCtryCcy rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getISOCtryCcyTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecISOCtryCcyObj realised = (ICFSecISOCtryCcyObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByCtryIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
		Iterator<ICFSecISOCtryCcyObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			@Override
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecISOCtryCcyObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readISOCtryCcyByCcyIdx( short ISOCcyId )
	{
		return( readISOCtryCcyByCcyIdx( ISOCcyId,
			false ) );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readISOCtryCcyByCcyIdx( short ISOCcyId,
		boolean forceRead )
	{
		final String S_ProcName = "readISOCtryCcyByCcyIdx";
		ICFSecISOCtryCcyByCcyIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
		key.setRequiredISOCcyId( ISOCcyId );
		Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict;
		if( indexByCcyIdx == null ) {
			indexByCcyIdx = new HashMap< ICFSecISOCtryCcyByCcyIdxKey,
				Map< ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > >();
		}
		if( ( ! forceRead ) && indexByCcyIdx.containsKey( key ) ) {
			dict = indexByCcyIdx.get( key );
		}
		else {
			dict = new HashMap<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj>();
			ICFSecISOCtryCcyObj obj;
			ICFSecISOCtryCcy[] recList = schema.getCFSecBackingStore().getTableISOCtryCcy().readDerivedByCcyIdx( null,
				ISOCcyId );
			ICFSecISOCtryCcy rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getISOCtryCcyTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecISOCtryCcyObj realised = (ICFSecISOCtryCcyObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByCcyIdx.put( key, dict );
		}
		int len = dict.size();
		ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
		Iterator<ICFSecISOCtryCcyObj> valIter = dict.values().iterator();
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
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			@Override
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecISOCtryCcyObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFSecISOCtryCcyObj readCachedISOCtryCcyByIdIdx( short ISOCtryId,
		short ISOCcyId )
	{
		ICFSecISOCtryCcyObj obj = null;
		ICFSecISOCtryCcyPKey pkey = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newPKey();
		pkey.setRequiredContainerCtry(ISOCtryId);
		pkey.setRequiredParentCcy(ISOCcyId);
		pkey.setRequiredContainerCtry(ISOCtryId);
		pkey.setRequiredParentCcy(ISOCcyId);
		obj = readCachedISOCtryCcy( pkey );
		return( obj );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readCachedISOCtryCcyByCtryIdx( short ISOCtryId )
	{
		final String S_ProcName = "readCachedISOCtryCcyByCtryIdx";
		ICFSecISOCtryCcyByCtryIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
		key.setRequiredISOCtryId( ISOCtryId );
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>();
		if( indexByCtryIdx != null ) {
			Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict;
			if( indexByCtryIdx.containsKey( key ) ) {
				dict = indexByCtryIdx.get( key );
				int len = dict.size();
				ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
				Iterator<ICFSecISOCtryCcyObj> valIter = dict.values().iterator();
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
			ICFSecISOCtryCcyObj obj;
			Iterator<ICFSecISOCtryCcyObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			@Override
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public List<ICFSecISOCtryCcyObj> readCachedISOCtryCcyByCcyIdx( short ISOCcyId )
	{
		final String S_ProcName = "readCachedISOCtryCcyByCcyIdx";
		ICFSecISOCtryCcyByCcyIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
		key.setRequiredISOCcyId( ISOCcyId );
		ArrayList<ICFSecISOCtryCcyObj> arrayList = new ArrayList<ICFSecISOCtryCcyObj>();
		if( indexByCcyIdx != null ) {
			Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict;
			if( indexByCcyIdx.containsKey( key ) ) {
				dict = indexByCcyIdx.get( key );
				int len = dict.size();
				ICFSecISOCtryCcyObj arr[] = new ICFSecISOCtryCcyObj[len];
				Iterator<ICFSecISOCtryCcyObj> valIter = dict.values().iterator();
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
			ICFSecISOCtryCcyObj obj;
			Iterator<ICFSecISOCtryCcyObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFSecISOCtryCcyObj> cmp = new Comparator<ICFSecISOCtryCcyObj>() {
			@Override
			public int compare( ICFSecISOCtryCcyObj lhs, ICFSecISOCtryCcyObj rhs ) {
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
					ICFSecISOCtryCcyPKey lhsPKey = lhs.getPKey();
					ICFSecISOCtryCcyPKey rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public void deepDisposeISOCtryCcyByIdIdx( short ISOCtryId,
		short ISOCcyId )
	{
		ICFSecISOCtryCcyObj obj = readCachedISOCtryCcyByIdIdx( ISOCtryId,
				ISOCcyId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeISOCtryCcyByCtryIdx( short ISOCtryId )
	{
		final String S_ProcName = "deepDisposeISOCtryCcyByCtryIdx";
		ICFSecISOCtryCcyObj obj;
		List<ICFSecISOCtryCcyObj> arrayList = readCachedISOCtryCcyByCtryIdx( ISOCtryId );
		if( arrayList != null )  {
			Iterator<ICFSecISOCtryCcyObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeISOCtryCcyByCcyIdx( short ISOCcyId )
	{
		final String S_ProcName = "deepDisposeISOCtryCcyByCcyIdx";
		ICFSecISOCtryCcyObj obj;
		List<ICFSecISOCtryCcyObj> arrayList = readCachedISOCtryCcyByCcyIdx( ISOCcyId );
		if( arrayList != null )  {
			Iterator<ICFSecISOCtryCcyObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public ICFSecISOCtryCcyObj updateISOCtryCcy( ICFSecISOCtryCcyObj Obj ) {
		ICFSecISOCtryCcyObj obj = Obj;
		schema.getCFSecBackingStore().getTableISOCtryCcy().updateISOCtryCcy( null,
			Obj.getISOCtryCcyRec() );
		obj = (ICFSecISOCtryCcyObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteISOCtryCcy( ICFSecISOCtryCcyObj Obj ) {
		ICFSecISOCtryCcyObj obj = Obj;
		schema.getCFSecBackingStore().getTableISOCtryCcy().deleteISOCtryCcy( null,
			obj.getISOCtryCcyRec() );
		Obj.forget();
	}

	@Override
	public void deleteISOCtryCcyByIdIdx( short ISOCtryId,
		short ISOCcyId )
	{
		ICFSecISOCtryCcyObj obj = readISOCtryCcy(ISOCtryId,
				ISOCcyId);
		if( obj != null ) {
			ICFSecISOCtryCcyEditObj editObj = (ICFSecISOCtryCcyEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecISOCtryCcyEditObj)obj.beginEdit();
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
		deepDisposeISOCtryCcyByIdIdx( ISOCtryId,
				ISOCcyId );
	}

	@Override
	public void deleteISOCtryCcyByCtryIdx( short ISOCtryId )
	{
		ICFSecISOCtryCcyByCtryIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCtryIdxKey();
		key.setRequiredISOCtryId( ISOCtryId );
		if( indexByCtryIdx == null ) {
			indexByCtryIdx = new HashMap< ICFSecISOCtryCcyByCtryIdxKey,
				Map< ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > >();
		}
		if( indexByCtryIdx.containsKey( key ) ) {
			Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict = indexByCtryIdx.get( key );
			schema.getCFSecBackingStore().getTableISOCtryCcy().deleteISOCtryCcyByCtryIdx( null,
				ISOCtryId );
			Iterator<ICFSecISOCtryCcyObj> iter = dict.values().iterator();
			ICFSecISOCtryCcyObj obj;
			List<ICFSecISOCtryCcyObj> toForget = new LinkedList<ICFSecISOCtryCcyObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByCtryIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableISOCtryCcy().deleteISOCtryCcyByCtryIdx( null,
				ISOCtryId );
		}
		deepDisposeISOCtryCcyByCtryIdx( ISOCtryId );
	}

	@Override
	public void deleteISOCtryCcyByCcyIdx( short ISOCcyId )
	{
		ICFSecISOCtryCcyByCcyIdxKey key = schema.getCFSecBackingStore().getFactoryISOCtryCcy().newByCcyIdxKey();
		key.setRequiredISOCcyId( ISOCcyId );
		if( indexByCcyIdx == null ) {
			indexByCcyIdx = new HashMap< ICFSecISOCtryCcyByCcyIdxKey,
				Map< ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj > >();
		}
		if( indexByCcyIdx.containsKey( key ) ) {
			Map<ICFSecISOCtryCcyPKey, ICFSecISOCtryCcyObj> dict = indexByCcyIdx.get( key );
			schema.getCFSecBackingStore().getTableISOCtryCcy().deleteISOCtryCcyByCcyIdx( null,
				ISOCcyId );
			Iterator<ICFSecISOCtryCcyObj> iter = dict.values().iterator();
			ICFSecISOCtryCcyObj obj;
			List<ICFSecISOCtryCcyObj> toForget = new LinkedList<ICFSecISOCtryCcyObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByCcyIdx.remove( key );
		}
		else {
			schema.getCFSecBackingStore().getTableISOCtryCcy().deleteISOCtryCcyByCcyIdx( null,
				ISOCcyId );
		}
		deepDisposeISOCtryCcyByCcyIdx( ISOCcyId );
	}
}