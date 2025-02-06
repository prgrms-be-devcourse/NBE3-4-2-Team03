'use client';
import { useState, useEffect } from 'react';

export default function ItemList() {
  const [categories, setCategories] = useState([]);
  const [items, setItems] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);

  useEffect(() => {
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
  }, []);

  const handleCategoryClick = (categoryId) => {
    setSelectedCategory(categoryId);
    fetch(`http://localhost:8080/api/admin/items?categoryId=${categoryId}`)
      .then((response) => response.json())
      .then((data) => setItems(data))
      .catch((error) => console.error('부품 로딩 실패:', error));
  };

  return (
    <div className="min-h-screen p-8 bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white">
      <h1 className="text-3xl font-bold text-center mb-8">카테고리와 부품 목록</h1>

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

      {selectedCategory && (
        <div>
          <h2 className="text-2xl font-semibold text-center mb-4">
            {categories.find(c => c.id === selectedCategory)?.category} 부품 목록
          </h2>
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-4">
            {items.length > 0 ? (
              items.map((item) => (
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
