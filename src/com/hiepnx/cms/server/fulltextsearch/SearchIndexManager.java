package com.hiepnx.cms.server.fulltextsearch;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.hiepnx.cms.shared.AccentRemover;
import com.hiepnx.cms.shared.Constants;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.UserInfo;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

public enum SearchIndexManager {
	INSTANCE;

	public List<IBasic> search(String keyword, String nameSpace) {
		keyword = "~" + keyword;
		Results<ScoredDocument> results = this.retrieveDocuments(AccentRemover.removeAccent(keyword).toLowerCase(),
				nameSpace);
		return search(results, nameSpace);
	}

	private List<IBasic> search(Results<ScoredDocument> results, String nameSpace) {
		if (nameSpace.equalsIgnoreCase(Constants.USER_INDEX_NAME)) {
			List<String> ids = getSearchUserIds(results.getResults());
			if (ids != null && !ids.isEmpty()) {
				return new ArrayList<IBasic>(ofy().load().type(UserInfo.class).ids(ids).values());
			}
		}
		return new ArrayList<IBasic>();
	}

	private List<String> getSearchUserIds(Collection<ScoredDocument> documents) {
		List<String> ids = new ArrayList<String>();
		for (ScoredDocument document : documents) {
			ids.add((document.getId()));
		}
		return ids;
	}

	public void addFullTextSearch(IBasic ibasic) {
		try {
			if (ibasic instanceof UserInfo) {
				UserInfo userInfo = (UserInfo) ibasic;
				Builder builder = Document.newBuilder().setId(userInfo.getId())
						.addField(Field.newBuilder().setName("account")
								.setText(AccentRemover.removeAccent(userInfo.getAccount().toLowerCase())))
						.addField(Field.newBuilder().setName("email")
								.setText(AccentRemover.removeAccent(userInfo.getEmail().toLowerCase())));
				if (userInfo.getPhoneNumber() != null && !userInfo.getPhoneNumber().isEmpty()) {
					builder.addField(Field.newBuilder().setName("phoneNumber")
							.setText(AccentRemover.removeAccent(userInfo.getPhoneNumber())));
				}
				if (userInfo.getName() != null && !userInfo.getName().isEmpty()) {
					builder.addField(Field.newBuilder().setName("name")
							.setText(AccentRemover.removeAccent(userInfo.getName().toLowerCase())));
				}
				if (userInfo.getFullName() != null && !userInfo.getFullName().isEmpty()) {
					builder.addField(Field.newBuilder().setName("fullName")
							.setText(AccentRemover.removeAccent(userInfo.getFullName().toLowerCase())));
				}
				SearchIndexManager.INSTANCE.indexDocument(Constants.USER_INDEX_NAME, builder.build());
			}
		} catch (Exception e) {
			System.out.println("add fulltext search error : " + e.toString());
		}
	}

	public void deleteFromFullTextSearch(IBasic item, String index) {
		if (item instanceof UserInfo) {
			this.deleteDocument(item.getId() + "", index);
		}
	}

	/**
	 * This method is used to add a Document to a particular Index
	 * 
	 * @param indexName This is the name of the Index to which the document is to be
	 *                  added. An index serves as a logical collection of documents
	 * @param document  This is the Document instance that needs to be added to the
	 *                  Index
	 */
	public void indexDocument(String indexName, Document document) {
		// Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		try {
			// Put the Document in the Index. If the document is already existing, it will
			// be overwritten
			index.put(document);
		} catch (PutException e) {
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
				// retry putting the document
			}
		}
	}

	/**
	 * This method is used to retrieve a particular Document from the Index
	 * 
	 * @param documentId This is the key field that uniquely identifies a document
	 *                   in the collection i.e. the Index. In our case it is the
	 *                   user id
	 * @return An instance of the Document object from the Index.
	 */
	public Document retrieveDocument(String documentId, String indexName) {
		// Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		// Retrieve the Record from the Index
		return index.get(documentId);
	}

	public void deleteDocument(String documentId, String indexName) {
		// Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		// Retrieve the Record from the Index
		index.delete(documentId);
	}

	/**
	 * This method is used to retrieve a list of documents from the Index that match
	 * the Search Term.
	 * 
	 * @param searchText The search term to find matching documents. By default, if
	 *                   you do not use the Search Language Syntax, it will retrieve
	 *                   all the records that contain a partial or full text match
	 *                   for all attributes of a document
	 * @return A collection of Documents that were found
	 */
	public Results<ScoredDocument> retrieveDocuments(String searchText, String indexName) {
		// Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		// Retrieve the Records from the Index
		return index.search(searchText);
	}

	/**
	 * This method is used to delete a document from the Index
	 * 
	 * @param documentId This is the key field that uniquely identifies a document
	 *                   in the collection i.e. the Index. In our case it is the
	 *                   user id
	 */

	public void deleteDocumentFromIndex(String documentId, String indexName) {
		// Setup the Index
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);

		// Delete the Records from the Index
		index.delete(documentId);
	}
}
