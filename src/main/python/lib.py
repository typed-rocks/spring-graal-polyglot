import pygal

def to_pie(result_map: dict) -> str:
    chart = pygal.Pie()
    for key, value in result_map.items():
        chart.add(key, value)
    return chart.render(is_unicode=True, height=200)

to_pie
