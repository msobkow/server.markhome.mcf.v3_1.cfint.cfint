// Description: Java 25 Table Object implementation for ISOCcy.

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

public class CFIntISOCcyTableObj
	implements ICFIntISOCcyTableObj
{
	protected ICFIntSchemaObj schema;
	private Map<Short, ICFSecISOCcyObj> members;
	private Map<Short, ICFSecISOCcyObj> allISOCcy;
	private Map< ICFSecISOCcyByCcyCdIdxKey,
		ICFSecISOCcyObj > indexByCcyCdIdx;
	private Map< ICFSecISOCcyByCcyNmIdxKey,
		ICFSecISOCcyObj > indexByCcyNmIdx;
	public static String TABLE_NAME = "ISOCcy";
	public static String TABLE_DBNAME = "iso_ccy";

	public CFIntISOCcyTableObj() {
		schema = null;
		members = new HashMap<Short, ICFSecISOCcyObj>();
		allISOCcy = null;
		indexByCcyCdIdx = null;
		indexByCcyNmIdx = null;
	}

	public CFIntISOCcyTableObj( ICFSecSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<Short, ICFSecISOCcyObj>();
		allISOCcy = null;
		indexByCcyCdIdx = null;
		indexByCcyNmIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFSecISOCcyTableObj.getRuntimeClassCode();
	}	

	/**
	 *	Get the backing store schema's class code, which is hard-coded into the object hierarchy.
	 *
	 *	@return The hardcoded backing store class code for this object, which is only valid in that schema.
	 */
	public static int getBackingClassCode() {
		return( CFSecISOCcyTableObj.getBackingClassCode() );
	}

	/**
	 *	Get the runtime class code for this table; this value is only stable after the application is fully initialized.
	 *
	 *	@return runtimeClassCode
	 */
	public static int getRuntimeClassCode() {
		return( CFSecISOCcyTableObj.getRuntimeClassCode() );
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
		allISOCcy = null;
		indexByCcyCdIdx = null;
		indexByCcyNmIdx = null;
		List<ICFSecISOCcyObj> toForget = new LinkedList<ICFSecISOCcyObj>();
		ICFSecISOCcyObj cur = null;
		Iterator<ICFSecISOCcyObj> iter = members.values().iterator();
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
	 *	CFIntISOCcyObj.
	 */
	@Override
	public ICFSecISOCcyObj newInstance() {
		ICFSecISOCcyObj inst = new CFIntISOCcyObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntISOCcyObj.
	 */
	@Override
	public ICFSecISOCcyEditObj newEditInstance( ICFSecISOCcyObj orig ) {
		ICFSecISOCcyEditObj edit = new CFIntISOCcyEditObj( orig );
		return( edit );
	}

	@Override
	public ICFSecISOCcyObj realiseISOCcy( ICFSecISOCcyObj Obj ) {
		ICFSecISOCcyObj obj = Obj;
		Short pkey = obj.getPKey();
		ICFSecISOCcyObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFSecISOCcyObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByCcyCdIdx != null ) {
				ICFSecISOCcyByCcyCdIdxKey keyCcyCdIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
				keyCcyCdIdx.setRequiredISOCode( keepObj.getRequiredISOCode() );
				indexByCcyCdIdx.remove( keyCcyCdIdx );
			}

			if( indexByCcyNmIdx != null ) {
				ICFSecISOCcyByCcyNmIdxKey keyCcyNmIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
				keyCcyNmIdx.setRequiredName( keepObj.getRequiredName() );
				indexByCcyNmIdx.remove( keyCcyNmIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByCcyCdIdx != null ) {
				ICFSecISOCcyByCcyCdIdxKey keyCcyCdIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
				keyCcyCdIdx.setRequiredISOCode( keepObj.getRequiredISOCode() );
				indexByCcyCdIdx.put( keyCcyCdIdx, keepObj );
			}

			if( indexByCcyNmIdx != null ) {
				ICFSecISOCcyByCcyNmIdxKey keyCcyNmIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
				keyCcyNmIdx.setRequiredName( keepObj.getRequiredName() );
				indexByCcyNmIdx.put( keyCcyNmIdx, keepObj );
			}

			if( allISOCcy != null ) {
				allISOCcy.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allISOCcy != null ) {
				allISOCcy.put( keepObj.getPKey(), keepObj );
			}

			if( indexByCcyCdIdx != null ) {
				ICFSecISOCcyByCcyCdIdxKey keyCcyCdIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
				keyCcyCdIdx.setRequiredISOCode( keepObj.getRequiredISOCode() );
				indexByCcyCdIdx.put( keyCcyCdIdx, keepObj );
			}

			if( indexByCcyNmIdx != null ) {
				ICFSecISOCcyByCcyNmIdxKey keyCcyNmIdx =
					schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
				keyCcyNmIdx.setRequiredName( keepObj.getRequiredName() );
				indexByCcyNmIdx.put( keyCcyNmIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFSecISOCcyObj createISOCcy( ICFSecISOCcyObj Obj ) {
		ICFSecISOCcyObj obj = Obj;
		ICFSecISOCcy rec = obj.getISOCcyRec();
		schema.getCFSecBackingStore().getTableISOCcy().createISOCcy(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readISOCcy( Short pkey ) {
		return( readISOCcy( pkey, false ) );
	}

	@Override
	public ICFSecISOCcyObj readISOCcy( Short pkey, boolean forceRead ) {
		ICFSecISOCcyObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFSecISOCcy readRec = schema.getCFSecBackingStore().getTableISOCcy().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getISOCcyTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFSecISOCcyObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readCachedISOCcy( Short pkey ) {
		ICFSecISOCcyObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeISOCcy( ICFSecISOCcyObj obj )
	{
		final String S_ProcName = "CFIntISOCcyTableObj.reallyDeepDisposeISOCcy() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		Short pkey = obj.getPKey();
		ICFSecISOCcyObj existing = readCachedISOCcy( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFSecISOCcyByCcyCdIdxKey keyCcyCdIdx = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
		keyCcyCdIdx.setRequiredISOCode( existing.getRequiredISOCode() );

		ICFSecISOCcyByCcyNmIdxKey keyCcyNmIdx = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
		keyCcyNmIdx.setRequiredName( existing.getRequiredName() );


		schema.getISOCtryCcyTableObj().deepDisposeISOCtryCcyByCcyIdx( existing.getRequiredISOCcyId() );

		if( indexByCcyCdIdx != null ) {
			indexByCcyCdIdx.remove( keyCcyCdIdx );
		}

		if( indexByCcyNmIdx != null ) {
			indexByCcyNmIdx.remove( keyCcyNmIdx );
		}


	}
	@Override
	public void deepDisposeISOCcy( Short pkey ) {
		ICFSecISOCcyObj obj = readCachedISOCcy( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecISOCcyObj lockISOCcy( Short pkey ) {
		ICFSecISOCcyObj locked = null;
		ICFSecISOCcy lockRec = schema.getCFSecBackingStore().getTableISOCcy().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getISOCcyTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFSecISOCcyObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockISOCcy", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFSecISOCcyObj> readAllISOCcy() {
		return( readAllISOCcy( false ) );
	}

	@Override
	public List<ICFSecISOCcyObj> readAllISOCcy( boolean forceRead ) {
		final String S_ProcName = "readAllISOCcy";
		if( ( allISOCcy == null ) || forceRead ) {
			Map<Short, ICFSecISOCcyObj> map = new HashMap<Short,ICFSecISOCcyObj>();
			allISOCcy = map;
			ICFSecISOCcy[] recList = schema.getCFSecBackingStore().getTableISOCcy().readAllDerived( null );
			ICFSecISOCcy rec;
			ICFSecISOCcyObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFSecISOCcyObj realised = (ICFSecISOCcyObj)obj.realise();
			}
		}
		int len = allISOCcy.size();
		ICFSecISOCcyObj arr[] = new ICFSecISOCcyObj[len];
		Iterator<ICFSecISOCcyObj> valIter = allISOCcy.values().iterator();
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
		ArrayList<ICFSecISOCcyObj> arrayList = new ArrayList<ICFSecISOCcyObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFSecISOCcyObj> cmp = new Comparator<ICFSecISOCcyObj>() {
			@Override
			public int compare( ICFSecISOCcyObj lhs, ICFSecISOCcyObj rhs ) {
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
					Short lhsPKey = lhs.getPKey();
					Short rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		List<ICFSecISOCcyObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFSecISOCcyObj> readCachedAllISOCcy() {
		final String S_ProcName = "readCachedAllISOCcy";
		ArrayList<ICFSecISOCcyObj> arrayList = new ArrayList<ICFSecISOCcyObj>();
		if( allISOCcy != null ) {
			int len = allISOCcy.size();
			ICFSecISOCcyObj arr[] = new ICFSecISOCcyObj[len];
			Iterator<ICFSecISOCcyObj> valIter = allISOCcy.values().iterator();
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
		Comparator<ICFSecISOCcyObj> cmp = new Comparator<ICFSecISOCcyObj>() {
			public int compare( ICFSecISOCcyObj lhs, ICFSecISOCcyObj rhs ) {
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
					Short lhsPKey = lhs.getPKey();
					Short rhsPKey = rhs.getPKey();
					int ret = lhsPKey.compareTo( rhsPKey );
					return( ret );
				}
			}
		};
		Collections.sort( arrayList, cmp );
		return( arrayList );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByIdIdx( short ISOCcyId )
	{
		return( readISOCcyByIdIdx( ISOCcyId,
			false ) );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByIdIdx( short ISOCcyId, boolean forceRead )
	{
		ICFSecISOCcyObj obj = readISOCcy( ISOCcyId, forceRead );
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByCcyCdIdx( String ISOCode )
	{
		return( readISOCcyByCcyCdIdx( ISOCode,
			false ) );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByCcyCdIdx( String ISOCode, boolean forceRead )
	{
		if( indexByCcyCdIdx == null ) {
			indexByCcyCdIdx = new HashMap< ICFSecISOCcyByCcyCdIdxKey,
				ICFSecISOCcyObj >();
		}
		ICFSecISOCcyByCcyCdIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
		key.setRequiredISOCode( ISOCode );
		ICFSecISOCcyObj obj = null;
		if( ( ! forceRead ) && indexByCcyCdIdx.containsKey( key ) ) {
			obj = indexByCcyCdIdx.get( key );
		}
		else {
			ICFSecISOCcy rec = schema.getCFSecBackingStore().getTableISOCcy().readDerivedByCcyCdIdx( null,
				ISOCode );
			if( rec != null ) {
				obj = schema.getISOCcyTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecISOCcyObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByCcyNmIdx( String Name )
	{
		return( readISOCcyByCcyNmIdx( Name,
			false ) );
	}

	@Override
	public ICFSecISOCcyObj readISOCcyByCcyNmIdx( String Name, boolean forceRead )
	{
		if( indexByCcyNmIdx == null ) {
			indexByCcyNmIdx = new HashMap< ICFSecISOCcyByCcyNmIdxKey,
				ICFSecISOCcyObj >();
		}
		ICFSecISOCcyByCcyNmIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
		key.setRequiredName( Name );
		ICFSecISOCcyObj obj = null;
		if( ( ! forceRead ) && indexByCcyNmIdx.containsKey( key ) ) {
			obj = indexByCcyNmIdx.get( key );
		}
		else {
			ICFSecISOCcy rec = schema.getCFSecBackingStore().getTableISOCcy().readDerivedByCcyNmIdx( null,
				Name );
			if( rec != null ) {
				obj = schema.getISOCcyTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFSecISOCcyObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readCachedISOCcyByIdIdx( short ISOCcyId )
	{
		ICFSecISOCcyObj obj = null;
		obj = readCachedISOCcy( ISOCcyId );
		return( obj );
	}

	@Override
	public ICFSecISOCcyObj readCachedISOCcyByCcyCdIdx( String ISOCode )
	{
		ICFSecISOCcyObj obj = null;
		ICFSecISOCcyByCcyCdIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
		key.setRequiredISOCode( ISOCode );
		if( indexByCcyCdIdx != null ) {
			if( indexByCcyCdIdx.containsKey( key ) ) {
				obj = indexByCcyCdIdx.get( key );
			}
			else {
				Iterator<ICFSecISOCcyObj> valIter = members.values().iterator();
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
			Iterator<ICFSecISOCcyObj> valIter = members.values().iterator();
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
	public ICFSecISOCcyObj readCachedISOCcyByCcyNmIdx( String Name )
	{
		ICFSecISOCcyObj obj = null;
		ICFSecISOCcyByCcyNmIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
		key.setRequiredName( Name );
		if( indexByCcyNmIdx != null ) {
			if( indexByCcyNmIdx.containsKey( key ) ) {
				obj = indexByCcyNmIdx.get( key );
			}
			else {
				Iterator<ICFSecISOCcyObj> valIter = members.values().iterator();
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
			Iterator<ICFSecISOCcyObj> valIter = members.values().iterator();
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
	public void deepDisposeISOCcyByIdIdx( short ISOCcyId )
	{
		ICFSecISOCcyObj obj = readCachedISOCcyByIdIdx( ISOCcyId );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeISOCcyByCcyCdIdx( String ISOCode )
	{
		ICFSecISOCcyObj obj = readCachedISOCcyByCcyCdIdx( ISOCode );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeISOCcyByCcyNmIdx( String Name )
	{
		ICFSecISOCcyObj obj = readCachedISOCcyByCcyNmIdx( Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFSecISOCcyObj updateISOCcy( ICFSecISOCcyObj Obj ) {
		ICFSecISOCcyObj obj = Obj;
		schema.getCFSecBackingStore().getTableISOCcy().updateISOCcy( null,
			Obj.getISOCcyRec() );
		obj = (ICFSecISOCcyObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteISOCcy( ICFSecISOCcyObj Obj ) {
		ICFSecISOCcyObj obj = Obj;
		schema.getCFSecBackingStore().getTableISOCcy().deleteISOCcy( null,
			obj.getISOCcyRec() );
		Obj.forget();
	}

	@Override
	public void deleteISOCcyByIdIdx( short ISOCcyId )
	{
		ICFSecISOCcyObj obj = readISOCcy(ISOCcyId);
		if( obj != null ) {
			ICFSecISOCcyEditObj editObj = (ICFSecISOCcyEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFSecISOCcyEditObj)obj.beginEdit();
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
		deepDisposeISOCcyByIdIdx( ISOCcyId );
	}

	@Override
	public void deleteISOCcyByCcyCdIdx( String ISOCode )
	{
		if( indexByCcyCdIdx == null ) {
			indexByCcyCdIdx = new HashMap< ICFSecISOCcyByCcyCdIdxKey,
				ICFSecISOCcyObj >();
		}
		ICFSecISOCcyByCcyCdIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyCdIdxKey();
		key.setRequiredISOCode( ISOCode );
		ICFSecISOCcyObj obj = null;
		if( indexByCcyCdIdx.containsKey( key ) ) {
			obj = indexByCcyCdIdx.get( key );
			schema.getCFSecBackingStore().getTableISOCcy().deleteISOCcyByCcyCdIdx( null,
				ISOCode );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableISOCcy().deleteISOCcyByCcyCdIdx( null,
				ISOCode );
		}
		deepDisposeISOCcyByCcyCdIdx( ISOCode );
	}

	@Override
	public void deleteISOCcyByCcyNmIdx( String Name )
	{
		if( indexByCcyNmIdx == null ) {
			indexByCcyNmIdx = new HashMap< ICFSecISOCcyByCcyNmIdxKey,
				ICFSecISOCcyObj >();
		}
		ICFSecISOCcyByCcyNmIdxKey key = schema.getCFSecBackingStore().getFactoryISOCcy().newByCcyNmIdxKey();
		key.setRequiredName( Name );
		ICFSecISOCcyObj obj = null;
		if( indexByCcyNmIdx.containsKey( key ) ) {
			obj = indexByCcyNmIdx.get( key );
			schema.getCFSecBackingStore().getTableISOCcy().deleteISOCcyByCcyNmIdx( null,
				Name );
			obj.forget();
		}
		else {
			schema.getCFSecBackingStore().getTableISOCcy().deleteISOCcyByCcyNmIdx( null,
				Name );
		}
		deepDisposeISOCcyByCcyNmIdx( Name );
	}
}