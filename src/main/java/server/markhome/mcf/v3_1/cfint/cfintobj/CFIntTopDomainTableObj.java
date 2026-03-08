// Description: Java 25 Table Object implementation for TopDomain.

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

public class CFIntTopDomainTableObj
	implements ICFIntTopDomainTableObj
{
	protected ICFIntSchemaObj schema;
	protected static int runtimeClassCode = ICFIntTopDomain.CLASS_CODE;
	protected static final int backingClassCode = ICFIntTopDomain.CLASS_CODE;
	private Map<CFLibDbKeyHash256, ICFIntTopDomainObj> members;
	private Map<CFLibDbKeyHash256, ICFIntTopDomainObj> allTopDomain;
	private Map< ICFIntTopDomainByTenantIdxKey,
		Map<CFLibDbKeyHash256, ICFIntTopDomainObj > > indexByTenantIdx;
	private Map< ICFIntTopDomainByTldIdxKey,
		Map<CFLibDbKeyHash256, ICFIntTopDomainObj > > indexByTldIdx;
	private Map< ICFIntTopDomainByNameIdxKey,
		ICFIntTopDomainObj > indexByNameIdx;
	public static String TABLE_NAME = "TopDomain";
	public static String TABLE_DBNAME = "tdomdef";

	public CFIntTopDomainTableObj() {
		schema = null;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTopDomainObj>();
		allTopDomain = null;
		indexByTenantIdx = null;
		indexByTldIdx = null;
		indexByNameIdx = null;
	}

	public CFIntTopDomainTableObj( ICFIntSchemaObj argSchema ) {
		schema = (ICFIntSchemaObj)argSchema;
		members = new HashMap<CFLibDbKeyHash256, ICFIntTopDomainObj>();
		allTopDomain = null;
		indexByTenantIdx = null;
		indexByTldIdx = null;
		indexByNameIdx = null;
	}
	
	/**
	 *	Get class code always returns the runtime class code for the objects, which is not stable until the application is done initializing and registering its objects.
	 *
	 *	@return runtime classcode
	 */ 
	@Override
	public int getClassCode() {
		return CFIntTopDomainTableObj.getRuntimeClassCode();
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
			throw new CFLibArgumentUnderflowException(CFIntTopDomainTableObj.class, "setRuntimeClassCode", 1, "argNewClassCode", argNewClassCode, 1);
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
		allTopDomain = null;
		indexByTenantIdx = null;
		indexByTldIdx = null;
		indexByNameIdx = null;
		List<ICFIntTopDomainObj> toForget = new LinkedList<ICFIntTopDomainObj>();
		ICFIntTopDomainObj cur = null;
		Iterator<ICFIntTopDomainObj> iter = members.values().iterator();
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
	 *	CFIntTopDomainObj.
	 */
	@Override
	public ICFIntTopDomainObj newInstance() {
		ICFIntTopDomainObj inst = new CFIntTopDomainObj( schema );
		return( inst );
	}

	/**
	 *	If your implementation subclasses the objects,
	 *	you'll want to overload the constructByClassCode()
	 *	implementation to return your implementation's
	 *	instances instead of the base implementation.
	 *
	 *	This is the sole factory for instances derived from
	 *	CFIntTopDomainObj.
	 */
	@Override
	public ICFIntTopDomainEditObj newEditInstance( ICFIntTopDomainObj orig ) {
		ICFIntTopDomainEditObj edit = new CFIntTopDomainEditObj( orig );
		return( edit );
	}

	@Override
	public ICFIntTopDomainObj realiseTopDomain( ICFIntTopDomainObj Obj ) {
		ICFIntTopDomainObj obj = Obj;
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTopDomainObj keepObj = null;
		if( members.containsKey( pkey ) && ( null != members.get( pkey ) ) ) {
			ICFIntTopDomainObj existingObj = members.get( pkey );
			keepObj = existingObj;

			/*
			 *	We always rebind the data because if we're being called, some index has
			 *	been updated and is refreshing it's data, which may or may not have changed
			 */

			// Detach object from alternate and duplicate indexes, leave PKey alone

			if( indexByTenantIdx != null ) {
				ICFIntTopDomainByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.remove( keepObj.getPKey() );
					if( mapTenantIdx.size() <= 0 ) {
						indexByTenantIdx.remove( keyTenantIdx );
					}
				}
			}

			if( indexByTldIdx != null ) {
				ICFIntTopDomainByTldIdxKey keyTldIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
				keyTldIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTldIdx = indexByTldIdx.get( keyTldIdx );
				if( mapTldIdx != null ) {
					mapTldIdx.remove( keepObj.getPKey() );
					if( mapTldIdx.size() <= 0 ) {
						indexByTldIdx.remove( keyTldIdx );
					}
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopDomainByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
				keyNameIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.remove( keyNameIdx );
			}

			keepObj.setRec( Obj.getRec() );
			// Attach new object to alternate and duplicate indexes -- PKey stay stable

			if( indexByTenantIdx != null ) {
				ICFIntTopDomainByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTldIdx != null ) {
				ICFIntTopDomainByTldIdxKey keyTldIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
				keyTldIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTldIdx = indexByTldIdx.get( keyTldIdx );
				if( mapTldIdx != null ) {
					mapTldIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopDomainByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
				keyNameIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

			if( allTopDomain != null ) {
				allTopDomain.put( keepObj.getPKey(), keepObj );
			}
		}
		else {
			keepObj = obj;
			keepObj.setIsNew( false );

			// Attach new object to PKey, all, alternate, and duplicate indexes
			members.put( keepObj.getPKey(), keepObj );
			if( allTopDomain != null ) {
				allTopDomain.put( keepObj.getPKey(), keepObj );
			}

			if( indexByTenantIdx != null ) {
				ICFIntTopDomainByTenantIdxKey keyTenantIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
				keyTenantIdx.setRequiredTenantId( keepObj.getRequiredTenantId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTenantIdx = indexByTenantIdx.get( keyTenantIdx );
				if( mapTenantIdx != null ) {
					mapTenantIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByTldIdx != null ) {
				ICFIntTopDomainByTldIdxKey keyTldIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
				keyTldIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				Map<CFLibDbKeyHash256, ICFIntTopDomainObj > mapTldIdx = indexByTldIdx.get( keyTldIdx );
				if( mapTldIdx != null ) {
					mapTldIdx.put( keepObj.getPKey(), keepObj );
				}
			}

			if( indexByNameIdx != null ) {
				ICFIntTopDomainByNameIdxKey keyNameIdx =
					schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
				keyNameIdx.setRequiredTldId( keepObj.getRequiredTldId() );
				keyNameIdx.setRequiredName( keepObj.getRequiredName() );
				indexByNameIdx.put( keyNameIdx, keepObj );
			}

		}
		return( keepObj );
	}

	@Override
	public ICFIntTopDomainObj createTopDomain( ICFIntTopDomainObj Obj ) {
		ICFIntTopDomainObj obj = Obj;
		ICFIntTopDomain rec = obj.getTopDomainRec();
		schema.getCFIntBackingStore().getTableTopDomain().createTopDomain(
			null,
			rec );
		obj.copyRecToPKey();
		obj = obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public ICFIntTopDomainObj readTopDomain( CFLibDbKeyHash256 pkey ) {
		return( readTopDomain( pkey, false ) );
	}

	@Override
	public ICFIntTopDomainObj readTopDomain( CFLibDbKeyHash256 pkey, boolean forceRead ) {
		ICFIntTopDomainObj obj = null;
		if( ( ! forceRead ) && members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		else {
			ICFIntTopDomain readRec = schema.getCFIntBackingStore().getTableTopDomain().readDerivedByIdIdx( null,
						pkey );
			if( readRec != null ) {
				obj = schema.getTopDomainTableObj().newInstance();
				obj.setPKey( readRec.getPKey() );
				obj.setRec( readRec );
				obj = (ICFIntTopDomainObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTopDomainObj readCachedTopDomain( CFLibDbKeyHash256 pkey ) {
		ICFIntTopDomainObj obj = null;
		if( members.containsKey( pkey ) ) {
			obj = members.get( pkey );
		}
		return( obj );
	}

	@Override
	public void reallyDeepDisposeTopDomain( ICFIntTopDomainObj obj )
	{
		final String S_ProcName = "CFIntTopDomainTableObj.reallyDeepDisposeTopDomain() ";
		String classCode;
		if( obj == null ) {
			return;
		}
		CFLibDbKeyHash256 pkey = obj.getPKey();
		ICFIntTopDomainObj existing = readCachedTopDomain( pkey );
		if( existing == null ) {
			return;
		}
		members.remove( pkey );
		ICFIntTopDomainByTenantIdxKey keyTenantIdx = schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
		keyTenantIdx.setRequiredTenantId( existing.getRequiredTenantId() );

		ICFIntTopDomainByTldIdxKey keyTldIdx = schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
		keyTldIdx.setRequiredTldId( existing.getRequiredTldId() );

		ICFIntTopDomainByNameIdxKey keyNameIdx = schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
		keyNameIdx.setRequiredTldId( existing.getRequiredTldId() );
		keyNameIdx.setRequiredName( existing.getRequiredName() );


					schema.getTopProjectTableObj().deepDisposeTopProjectByTopDomainIdx( existing.getRequiredId() );

		if( indexByTenantIdx != null ) {
			if( indexByTenantIdx.containsKey( keyTenantIdx ) ) {
				indexByTenantIdx.get( keyTenantIdx ).remove( pkey );
				if( indexByTenantIdx.get( keyTenantIdx ).size() <= 0 ) {
					indexByTenantIdx.remove( keyTenantIdx );
				}
			}
		}

		if( indexByTldIdx != null ) {
			if( indexByTldIdx.containsKey( keyTldIdx ) ) {
				indexByTldIdx.get( keyTldIdx ).remove( pkey );
				if( indexByTldIdx.get( keyTldIdx ).size() <= 0 ) {
					indexByTldIdx.remove( keyTldIdx );
				}
			}
		}

		if( indexByNameIdx != null ) {
			indexByNameIdx.remove( keyNameIdx );
		}


	}
	@Override
	public void deepDisposeTopDomain( CFLibDbKeyHash256 pkey ) {
		ICFIntTopDomainObj obj = readCachedTopDomain( pkey );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTopDomainObj lockTopDomain( CFLibDbKeyHash256 pkey ) {
		ICFIntTopDomainObj locked = null;
		ICFIntTopDomain lockRec = schema.getCFIntBackingStore().getTableTopDomain().lockDerived( null, pkey );
		if( lockRec != null ) {
				locked = schema.getTopDomainTableObj().newInstance();
			locked.setRec( lockRec );
			locked.setPKey( lockRec.getPKey() );
			locked = (ICFIntTopDomainObj)locked.realise();
		}
		else {
			throw new CFLibCollisionDetectedException( getClass(), "lockTopDomain", pkey );
		}
		return( locked );
	}

	@Override
	public List<ICFIntTopDomainObj> readAllTopDomain() {
		return( readAllTopDomain( false ) );
	}

	@Override
	public List<ICFIntTopDomainObj> readAllTopDomain( boolean forceRead ) {
		final String S_ProcName = "readAllTopDomain";
		if( ( allTopDomain == null ) || forceRead ) {
			Map<CFLibDbKeyHash256, ICFIntTopDomainObj> map = new HashMap<CFLibDbKeyHash256,ICFIntTopDomainObj>();
			allTopDomain = map;
			ICFIntTopDomain[] recList = schema.getCFIntBackingStore().getTableTopDomain().readAllDerived( null );
			ICFIntTopDomain rec;
			ICFIntTopDomainObj obj;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopDomainObj realised = (ICFIntTopDomainObj)obj.realise();
			}
		}
		int len = allTopDomain.size();
		ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
		Iterator<ICFIntTopDomainObj> valIter = allTopDomain.values().iterator();
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
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			@Override
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
		List<ICFIntTopDomainObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntTopDomainObj> readCachedAllTopDomain() {
		final String S_ProcName = "readCachedAllTopDomain";
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>();
		if( allTopDomain != null ) {
			int len = allTopDomain.size();
			ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
			Iterator<ICFIntTopDomainObj> valIter = allTopDomain.values().iterator();
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
		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
	public ICFIntTopDomainObj readTopDomainByIdIdx( CFLibDbKeyHash256 Id )
	{
		return( readTopDomainByIdIdx( Id,
			false ) );
	}

	@Override
	public ICFIntTopDomainObj readTopDomainByIdIdx( CFLibDbKeyHash256 Id, boolean forceRead )
	{
		ICFIntTopDomainObj obj = readTopDomain( Id, forceRead );
		return( obj );
	}

	@Override
	public List<ICFIntTopDomainObj> readTopDomainByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		return( readTopDomainByTenantIdx( TenantId,
			false ) );
	}

	@Override
	public List<ICFIntTopDomainObj> readTopDomainByTenantIdx( CFLibDbKeyHash256 TenantId,
		boolean forceRead )
	{
		final String S_ProcName = "readTopDomainByTenantIdx";
		ICFIntTopDomainByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict;
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTopDomainByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopDomainObj > >();
		}
		if( ( ! forceRead ) && indexByTenantIdx.containsKey( key ) ) {
			dict = indexByTenantIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntTopDomainObj>();
			ICFIntTopDomainObj obj;
			ICFIntTopDomain[] recList = schema.getCFIntBackingStore().getTableTopDomain().readDerivedByTenantIdx( null,
				TenantId );
			ICFIntTopDomain rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTopDomainTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopDomainObj realised = (ICFIntTopDomainObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTenantIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
		Iterator<ICFIntTopDomainObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			@Override
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
		List<ICFIntTopDomainObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public List<ICFIntTopDomainObj> readTopDomainByTldIdx( CFLibDbKeyHash256 TldId )
	{
		return( readTopDomainByTldIdx( TldId,
			false ) );
	}

	@Override
	public List<ICFIntTopDomainObj> readTopDomainByTldIdx( CFLibDbKeyHash256 TldId,
		boolean forceRead )
	{
		final String S_ProcName = "readTopDomainByTldIdx";
		ICFIntTopDomainByTldIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
		key.setRequiredTldId( TldId );
		Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict;
		if( indexByTldIdx == null ) {
			indexByTldIdx = new HashMap< ICFIntTopDomainByTldIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopDomainObj > >();
		}
		if( ( ! forceRead ) && indexByTldIdx.containsKey( key ) ) {
			dict = indexByTldIdx.get( key );
		}
		else {
			dict = new HashMap<CFLibDbKeyHash256, ICFIntTopDomainObj>();
			ICFIntTopDomainObj obj;
			ICFIntTopDomain[] recList = schema.getCFIntBackingStore().getTableTopDomain().readDerivedByTldIdx( null,
				TldId );
			ICFIntTopDomain rec;
			for( int idx = 0; idx < recList.length; idx ++ ) {
				rec = recList[ idx ];
				obj = schema.getTopDomainTableObj().newInstance();
				obj.setPKey( rec.getPKey() );
				obj.setRec( rec );
				ICFIntTopDomainObj realised = (ICFIntTopDomainObj)obj.realise();
				dict.put( realised.getPKey(), realised );
			}
			indexByTldIdx.put( key, dict );
		}
		int len = dict.size();
		ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
		Iterator<ICFIntTopDomainObj> valIter = dict.values().iterator();
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
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>(len);
		for( idx = 0; idx < len; idx ++ ) {
			arrayList.add( arr[idx] );
		}

		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			@Override
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
		List<ICFIntTopDomainObj> sortedList = arrayList;
		return( sortedList );
	}

	@Override
	public ICFIntTopDomainObj readTopDomainByNameIdx( CFLibDbKeyHash256 TldId,
		String Name )
	{
		return( readTopDomainByNameIdx( TldId,
			Name,
			false ) );
	}

	@Override
	public ICFIntTopDomainObj readTopDomainByNameIdx( CFLibDbKeyHash256 TldId,
		String Name, boolean forceRead )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTopDomainByNameIdxKey,
				ICFIntTopDomainObj >();
		}
		ICFIntTopDomainByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
		key.setRequiredTldId( TldId );
		key.setRequiredName( Name );
		ICFIntTopDomainObj obj = null;
		if( ( ! forceRead ) && indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
		}
		else {
			ICFIntTopDomain rec = schema.getCFIntBackingStore().getTableTopDomain().readDerivedByNameIdx( null,
				TldId,
				Name );
			if( rec != null ) {
				obj = schema.getTopDomainTableObj().newInstance();
				obj.setRec( rec );
				obj.setPKey( rec.getPKey() );
				obj = (ICFIntTopDomainObj)obj.realise();
			}
		}
		return( obj );
	}

	@Override
	public ICFIntTopDomainObj readCachedTopDomainByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopDomainObj obj = null;
		obj = readCachedTopDomain( Id );
		return( obj );
	}

	@Override
	public List<ICFIntTopDomainObj> readCachedTopDomainByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "readCachedTopDomainByTenantIdx";
		ICFIntTopDomainByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>();
		if( indexByTenantIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict;
			if( indexByTenantIdx.containsKey( key ) ) {
				dict = indexByTenantIdx.get( key );
				int len = dict.size();
				ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
				Iterator<ICFIntTopDomainObj> valIter = dict.values().iterator();
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
			ICFIntTopDomainObj obj;
			Iterator<ICFIntTopDomainObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			@Override
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
	public List<ICFIntTopDomainObj> readCachedTopDomainByTldIdx( CFLibDbKeyHash256 TldId )
	{
		final String S_ProcName = "readCachedTopDomainByTldIdx";
		ICFIntTopDomainByTldIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
		key.setRequiredTldId( TldId );
		ArrayList<ICFIntTopDomainObj> arrayList = new ArrayList<ICFIntTopDomainObj>();
		if( indexByTldIdx != null ) {
			Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict;
			if( indexByTldIdx.containsKey( key ) ) {
				dict = indexByTldIdx.get( key );
				int len = dict.size();
				ICFIntTopDomainObj arr[] = new ICFIntTopDomainObj[len];
				Iterator<ICFIntTopDomainObj> valIter = dict.values().iterator();
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
			ICFIntTopDomainObj obj;
			Iterator<ICFIntTopDomainObj> valIter = members.values().iterator();
			while( valIter.hasNext() ) {
				obj = valIter.next();
				if( obj != null ) {
					if( obj.getRec().compareTo( key ) == 0 ) {
						arrayList.add( obj );
					}
				}
			}
		}
		Comparator<ICFIntTopDomainObj> cmp = new Comparator<ICFIntTopDomainObj>() {
			@Override
			public int compare( ICFIntTopDomainObj lhs, ICFIntTopDomainObj rhs ) {
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
	public ICFIntTopDomainObj readCachedTopDomainByNameIdx( CFLibDbKeyHash256 TldId,
		String Name )
	{
		ICFIntTopDomainObj obj = null;
		ICFIntTopDomainByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
		key.setRequiredTldId( TldId );
		key.setRequiredName( Name );
		if( indexByNameIdx != null ) {
			if( indexByNameIdx.containsKey( key ) ) {
				obj = indexByNameIdx.get( key );
			}
			else {
				Iterator<ICFIntTopDomainObj> valIter = members.values().iterator();
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
			Iterator<ICFIntTopDomainObj> valIter = members.values().iterator();
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
	public void deepDisposeTopDomainByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopDomainObj obj = readCachedTopDomainByIdIdx( Id );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public void deepDisposeTopDomainByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		final String S_ProcName = "deepDisposeTopDomainByTenantIdx";
		ICFIntTopDomainObj obj;
		List<ICFIntTopDomainObj> arrayList = readCachedTopDomainByTenantIdx( TenantId );
		if( arrayList != null )  {
			Iterator<ICFIntTopDomainObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTopDomainByTldIdx( CFLibDbKeyHash256 TldId )
	{
		final String S_ProcName = "deepDisposeTopDomainByTldIdx";
		ICFIntTopDomainObj obj;
		List<ICFIntTopDomainObj> arrayList = readCachedTopDomainByTldIdx( TldId );
		if( arrayList != null )  {
			Iterator<ICFIntTopDomainObj> arrayIter = arrayList.iterator();
			while( arrayIter.hasNext() ) {
				obj = arrayIter.next();
				if( obj != null ) {
					obj.forget();
				}
			}
		}
	}

	@Override
	public void deepDisposeTopDomainByNameIdx( CFLibDbKeyHash256 TldId,
		String Name )
	{
		ICFIntTopDomainObj obj = readCachedTopDomainByNameIdx( TldId,
				Name );
		if( obj != null ) {
			obj.forget();
		}
	}

	@Override
	public ICFIntTopDomainObj updateTopDomain( ICFIntTopDomainObj Obj ) {
		ICFIntTopDomainObj obj = Obj;
		schema.getCFIntBackingStore().getTableTopDomain().updateTopDomain( null,
			Obj.getTopDomainRec() );
		obj = (ICFIntTopDomainObj)Obj.realise();
		obj.endEdit();
		return( obj );
	}

	@Override
	public void deleteTopDomain( ICFIntTopDomainObj Obj ) {
		ICFIntTopDomainObj obj = Obj;
		schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomain( null,
			obj.getTopDomainRec() );
		Obj.forget();
	}

	@Override
	public void deleteTopDomainByIdIdx( CFLibDbKeyHash256 Id )
	{
		ICFIntTopDomainObj obj = readTopDomain(Id);
		if( obj != null ) {
			ICFIntTopDomainEditObj editObj = (ICFIntTopDomainEditObj)obj.getEdit();
			boolean editStarted;
			if( editObj == null ) {
				editObj = (ICFIntTopDomainEditObj)obj.beginEdit();
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
		deepDisposeTopDomainByIdIdx( Id );
	}

	@Override
	public void deleteTopDomainByTenantIdx( CFLibDbKeyHash256 TenantId )
	{
		ICFIntTopDomainByTenantIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTenantIdxKey();
		key.setRequiredTenantId( TenantId );
		if( indexByTenantIdx == null ) {
			indexByTenantIdx = new HashMap< ICFIntTopDomainByTenantIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopDomainObj > >();
		}
		if( indexByTenantIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict = indexByTenantIdx.get( key );
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByTenantIdx( null,
				TenantId );
			Iterator<ICFIntTopDomainObj> iter = dict.values().iterator();
			ICFIntTopDomainObj obj;
			List<ICFIntTopDomainObj> toForget = new LinkedList<ICFIntTopDomainObj>();
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
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByTenantIdx( null,
				TenantId );
		}
		deepDisposeTopDomainByTenantIdx( TenantId );
	}

	@Override
	public void deleteTopDomainByTldIdx( CFLibDbKeyHash256 TldId )
	{
		ICFIntTopDomainByTldIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByTldIdxKey();
		key.setRequiredTldId( TldId );
		if( indexByTldIdx == null ) {
			indexByTldIdx = new HashMap< ICFIntTopDomainByTldIdxKey,
				Map< CFLibDbKeyHash256, ICFIntTopDomainObj > >();
		}
		if( indexByTldIdx.containsKey( key ) ) {
			Map<CFLibDbKeyHash256, ICFIntTopDomainObj> dict = indexByTldIdx.get( key );
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByTldIdx( null,
				TldId );
			Iterator<ICFIntTopDomainObj> iter = dict.values().iterator();
			ICFIntTopDomainObj obj;
			List<ICFIntTopDomainObj> toForget = new LinkedList<ICFIntTopDomainObj>();
			while( iter.hasNext() ) {
				obj = iter.next();
				toForget.add( obj );
			}
			iter = toForget.iterator();
			while( iter.hasNext() ) {
				obj = iter.next();
				obj.forget();
			}
			indexByTldIdx.remove( key );
		}
		else {
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByTldIdx( null,
				TldId );
		}
		deepDisposeTopDomainByTldIdx( TldId );
	}

	@Override
	public void deleteTopDomainByNameIdx( CFLibDbKeyHash256 TldId,
		String Name )
	{
		if( indexByNameIdx == null ) {
			indexByNameIdx = new HashMap< ICFIntTopDomainByNameIdxKey,
				ICFIntTopDomainObj >();
		}
		ICFIntTopDomainByNameIdxKey key = schema.getCFIntBackingStore().getFactoryTopDomain().newByNameIdxKey();
		key.setRequiredTldId( TldId );
		key.setRequiredName( Name );
		ICFIntTopDomainObj obj = null;
		if( indexByNameIdx.containsKey( key ) ) {
			obj = indexByNameIdx.get( key );
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByNameIdx( null,
				TldId,
				Name );
			obj.forget();
		}
		else {
			schema.getCFIntBackingStore().getTableTopDomain().deleteTopDomainByNameIdx( null,
				TldId,
				Name );
		}
		deepDisposeTopDomainByNameIdx( TldId,
				Name );
	}
}