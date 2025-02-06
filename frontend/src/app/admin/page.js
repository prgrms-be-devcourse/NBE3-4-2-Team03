'use client';
import { useState, useEffect } from 'react';

export default function ItemList() {
  const [categories, setCategories] = useState([]);
  const [items, setItems] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [newCategoryName, setNewCategoryName] = useState('');

  useEffect(() => {
    fetchCategories();
    fetchItems();
  }, []);

  const fetchCategories = () => {
    fetch('http://localhost:8080/api/admin/categories')
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setCategories(data);
        } else {
          console.error('잘못된 데이터 형식:', data);
        }
      })
      .catch((error) => console.error('카테고리 로딩 실패:', error));
  };

  const fetchItems = () => {
    fetch('http://localhost:8080/api/admin/items')
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setItems(data);
        } else {
          console.error('잘못된 데이터 형식:', data);
        }
      })
      .catch((error) => console.error('부품 로딩 실패:', error));
  };

  const handleCategoryClick = (categoryId) => {
    setSelectedCategory(categoryId);
  };

  const handleAddCategory = () => {
    if (!newCategoryName.trim()) return alert('카테고리 이름을 입력하세요.');
    fetch('http://localhost:8080/api/admin/categories', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ category: newCategoryName }),
    })
      .then((response) => response.json())
      .then(() => {
        setNewCategoryName('');
        fetchCategories();
      })
      .catch((error) => console.error('카테고리 추가 실패:', error));
  };

  const handleUpdateCategory = () => {
    if (!selectedCategory) return alert('수정할 카테고리를 선택하세요.');
    const newName = prompt('새로운 카테고리 이름을 입력하세요:');
    if (!newName) return;

    fetch(`http://localhost:8080/api/admin/categories/${selectedCategory}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ category: newName }),
    })
      .then((response) => response.json())
      .then(() => fetchCategories())
      .catch((error) => console.error('카테고리 수정 실패:', error));
  };

  const handleDeleteCategory = () => {
    if (!selectedCategory) return alert('삭제할 카테고리를 선택하세요.');
    if (!confirm('정말로 삭제하시겠습니까?')) return;

    fetch(`http://localhost:8080/api/admin/categories/${selectedCategory}`, {
      method: 'DELETE',
    })
      .then((response) => response.json())
      .then(() => {
        setSelectedCategory(null);
        fetchCategories();
      })
      .catch((error) => console.error('카테고리 삭제 실패:', error));
  };

  return (
    <div className="min-h-screen p-8 bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white">
      <h1 className="text-3xl font-bold text-center mb-8">카테고리와 부품 목록</h1>

      {/* 카테고리 추가 */}
      <div className="flex justify-center mb-6 gap-4">
        <input
          type="text"
          value={newCategoryName}
          onChange={(e) => setNewCategoryName(e.target.value)}
          placeholder="새 카테고리 이름"
          className="px-4 py-2 border border-gray-300 rounded-lg dark:bg-gray-800 dark:text-white"
        />
        <button
          onClick={handleAddCategory}
          className="px-4 py-2 bg-green-600 text-white rounded-lg shadow-md hover:bg-green-700 transition"
        >
          추가
        </button>
      </div>

      {/* 카테고리 목록 */}
      <div className="flex flex-wrap justify-center gap-4 mb-8">
        {categories.length > 0 ? (
          categories.map((category) => (
            <button
              key={category.id}
              onClick={() => handleCategoryClick(category.id)}
              className={`px-6 py-3 rounded-lg text-lg font-medium transition-colors duration-300 shadow-md 
                ${selectedCategory === category.id ? 'bg-blue-600 text-white' : 'bg-white text-gray-900 border border-gray-300 hover:bg-blue-100 dark:bg-gray-800 dark:text-gray-300 dark:hover:bg-gray-700'}`}
            >
              {category.category}
            </button>
          ))
        ) : (
          <p>카테고리가 없습니다.</p>
        )}
      </div>

      {/* 카테고리 수정 & 삭제 */}
      {selectedCategory && (
        <div className="flex justify-center gap-4 mb-6">
          <button
            onClick={handleUpdateCategory}
            className="px-4 py-2 bg-yellow-500 text-white rounded-lg shadow-md hover:bg-yellow-600 transition"
          >
            수정
          </button>
          <button
            onClick={handleDeleteCategory}
            className="px-4 py-2 bg-red-600 text-white rounded-lg shadow-md hover:bg-red-700 transition"
          >
            삭제
          </button>
        </div>
      )}

      {/* 부품 목록 */}
      {selectedCategory && (
        <div>
          <h2 className="text-2xl font-semibold text-center mb-4">
            {categories.find(c => c.id === selectedCategory)?.category} 부품 목록
          </h2>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-4">
            {items.filter(item => item.categoryId === selectedCategory).length > 0 ? (
              items
                .filter(item => item.categoryId === selectedCategory)
                .map((item) => (
                  <div key={item.id} className="bg-white dark:bg-gray-800 p-4 rounded-lg shadow-lg text-center">
                    <img
                      src={`/images/${item.filename}`}
                      alt={item.name}
                      className="w-full h-32 object-cover mb-2 rounded-md"
                    />
                    <p className="text-lg font-medium">{item.name}</p>
                  </div>
                ))
            ) : (
              <p className="col-span-full text-center">해당 카테고리에 부품이 없습니다.</p>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
